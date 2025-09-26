package com.sp.response;

public class PaymentLinkResponse {

	
	private String payment_link_url;
	private String payment_id;
	
	public PaymentLinkResponse(String payment_link_url, String payment_id) {
		super();
		this.payment_link_url = payment_link_url;
		this.payment_id = payment_id;
	}

	public PaymentLinkResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPayment_link_url() {
		return payment_link_url;
	}

	public void setPayment_link_url(String payment_link_url) {
		this.payment_link_url = payment_link_url;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	
	

}