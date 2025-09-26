package com.sp.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sp.exception.OrderException;
import com.sp.model.Order;
import com.sp.model.PaymentDetails;
import com.sp.repository.OrderRepository;
import com.sp.response.ApiResponse;
import com.sp.response.PaymentLinkResponse;
import com.sp.service.OrderService;
import com.sp.service.UserService;
import com.zosh.user.domain.OrderStatus;
import com.zosh.user.domain.PaymentStatus;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Value("${razorpay.api.key}")
	String apiKey;
	
	@Value("${razorpay.api.secret}")
	String apiSecret;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException,RazorpayException{
		
		Order order= orderService.findOrderById(orderId);
		
		try {
			System.out.println(apiKey+" "+apiSecret);
			
			RazorpayClient razorpay=new RazorpayClient(apiKey,apiSecret);
			
			JSONObject paymentLinkRequest = new JSONObject();
			
			paymentLinkRequest.put("amount",order.getTotalPrice()*100);
			paymentLinkRequest.put("currency","INR");
			
			
			JSONObject customer = new JSONObject();
			customer.put("name",order.getUser().getFirstName());
			customer.put("email",order.getUser().getEmail());
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			paymentLinkRequest.put("reference_id", String.valueOf(orderId));

			paymentLinkRequest.put("callback_url","https://codewithsp-ecommerce.vercel.app/payments/"+orderId);
			paymentLinkRequest.put("callback_method","get");
			
			PaymentLink payment=razorpay.paymentLink.create(paymentLinkRequest);
			System.out.println(payment +"payment response ");
			String paymentLinkId=payment.get("id");
			String paymentLinkUrl=payment.get("short_url");
			
			PaymentLinkResponse res= new PaymentLinkResponse();
			System.out.println("payment id is "+paymentLinkId);
			res.setPayment_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
			PaymentDetails paydetails= new PaymentDetails();
			paydetails.setPaymentId(paymentLinkId);
			
			System.out.println(res.getPayment_id()+"is the value set for payment id ");
			
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
			
		}catch(Exception e) {
			throw new RazorpayException(e.getMessage());
		}
		
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse>redirect(@RequestParam(name="payment_id")String paymentId,@RequestParam(name="order_id")Long orderId) throws OrderException, RazorpayException{
		Order order =orderService.findOrderById(orderId);
		RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
		try {
			
			Payment payment = razorpay.payments.fetch(paymentId);
			System.out.println("The payment details will be "+payment);
			if(payment.get("status").equals("captured")) {
				System.out.println("payment details --- "+payment+payment.get("status"));
			  
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus("PLACED");
//				order.setOrderItems(order.getOrderItems());
				System.out.println(order.getPaymentDetails().getStatus()+"payment status ");
				orderRepository.save(order);
			}
			
			ApiResponse res= new ApiResponse();
			
			res.setMessage("Your order get placed ");
			res.setStatus(true);
			return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
			
		}catch(Exception e) {
			throw new RazorpayException(e.getMessage());
		}
	}
	
	
	
}
