export interface PaymentVnpay {
    content : string,
    amount : number
}

export interface PaymentVnpaySuccess {
    vnpUrl : string
}

export interface PaymentVnpayError {
    message : string
}

export interface Payment {
    courseId : number,
    amount : number,
    paymentMethodId : number
}