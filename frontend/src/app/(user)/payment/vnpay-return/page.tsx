'use client'

import React, { useEffect, useRef, useState } from 'react';
import { CheckCircle, XCircle, ArrowLeft, RefreshCw, CreditCard, Calendar, DollarSign } from 'lucide-react';
import { useHandlePayment } from '@/hooks/usePayments';
import { Payment } from '@/types/payments';
import { useSearchParams } from 'next/navigation';
import { format, parse } from 'date-fns';
import { useRouter } from 'next/navigation';
import { formatPrice } from '@/utils/formatters';

const PaymentStautsVnpay: React.FC = () => {
  const router = useRouter()
  const [paymentStatus, setPaymentStatus] = useState<'success' | 'failed'>('success');

  const searchParams = useSearchParams();
  const amount = searchParams.get('vnp_Amount')

  const codeTransaction = searchParams.get('vnp_TxnRef')

  const datePayment = searchParams.get('vnp_PayDate')
  const inputFormat = "yyyyMMddHHmmss";
  const outputFormat = "dd/MM/yyyy HH:mm:ss";
  const parsedDate = parse(String(datePayment), inputFormat, new Date());

  const descriptionPayment = searchParams.get("vnp_OrderInfo")

  const formattedDate = format(parsedDate, outputFormat);

  const { mutate, isError, isSuccess, data } = useHandlePayment();

  const hasMutated = useRef(false);
  useEffect(() => {
    const informationPayment = localStorage.getItem('informationPayment')
    if (informationPayment) {
      const parseInfo: Payment = JSON.parse(informationPayment);

      if (!hasMutated.current) {
        const paymentBody = {
          courseId: parseInfo.courseId,
          amount: parseInfo.amount,
          paymentMethodId: parseInfo.paymentMethodId
        };
        mutate(paymentBody);
        hasMutated.current = true;
        localStorage.removeItem('informationPayment')
      }
    }

  }, []);


  const handleGoBack = () => {
    router.push('/')
  };


  const successContent = (
    <div className="max-w-md mx-auto bg-white rounded-2xl shadow-2xl p-8 text-center">
      <div className="mb-6">
        <div className="mx-auto w-20 h-20 bg-green-100 rounded-full flex items-center justify-center mb-4">
          <CheckCircle className="w-12 h-12 text-green-600" />
        </div>
        <h1 className="text-2xl font-bold text-gray-900 mb-2">Thanh toán thành công!</h1>
        <p className="text-gray-600">Cảm ơn bạn đã mua hàng. Đơn hàng của bạn đang được xử lý.</p>
      </div>

      <div className="bg-gray-50 rounded-xl p-6 mb-6 space-y-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <CreditCard className="w-5 h-5 text-gray-500" />
            <span className="text-sm font-medium text-gray-700">Mã giao dịch</span>
          </div>
          <span className="text-sm font-mono text-gray-900">{codeTransaction}</span>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <DollarSign className="w-5 h-5 text-gray-500" />
            <span className="text-sm font-medium text-gray-700">Số tiền</span>
          </div>
          <span className="text-sm font-semibold text-gray-900">{formatPrice(Number(amount) / 100)}</span>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <Calendar className="w-5 h-5 text-gray-500" />
            <span className="text-sm font-medium text-gray-700">Ngày thanh toán</span>
          </div>
          <span className="text-sm text-gray-900">{formattedDate}</span>
        </div>

        <div className="flex justify-between items-start">
          <div className="flex-shrink-0 flex items-center space-x-3"> 
            <CreditCard className="w-5 h-5 text-gray-500" />
            <span className="text-sm font-medium text-gray-700 whitespace-nowrap">Nội dung</span> 
          </div>
          <span className="text-sm text-gray-900 text-right flex-grow break-words ml-4">{descriptionPayment}</span> 
        </div>
      </div>

      <div className="space-y-3">
        <button
          onClick={handleGoBack}
          className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-xl transition-colors duration-200"
        >
          Tiếp tục mua sắm
        </button>

        <button
          onClick={() => setPaymentStatus('failed')}
          className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium py-3 px-4 rounded-xl transition-colors duration-200"
        >
          Xem trạng thái thất bại
        </button>
      </div>
    </div>
  );

  const failedContent = (
    <div className="max-w-md mx-auto bg-white rounded-2xl shadow-2xl p-8 text-center">
      <div className="mb-6">
        <div className="mx-auto w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mb-4">
          <XCircle className="w-12 h-12 text-red-600" />
        </div>
        <h1 className="text-2xl font-bold text-gray-900 mb-2">Thanh toán thất bại!</h1>
        <p className="text-gray-600">Rất tiếc, giao dịch của bạn không thể hoàn thành. Vui lòng thử lại.</p>
      </div>

      <div className="bg-red-50 border border-red-200 rounded-xl p-6 mb-6">
        <div className="space-y-3">
          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-red-800">Mã lỗi</span>
            <span className="text-sm font-mono text-red-900">ERR_PAYMENT_DECLINED</span>
          </div>

          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-red-800">Mã giao dịch</span>
            <span className="text-sm font-mono text-red-900">{codeTransaction}</span>
          </div>

          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-red-800">Số tiền</span>
            <span className="text-sm font-semibold text-red-900">{formatPrice(Number(amount) / 100)}</span>
          </div>

          <div className="flex items-center justify-between">
            <span className="text-sm font-medium text-red-800">Thời gian</span>
            <span className="text-sm text-red-900">{formattedDate}</span>
          </div>
        </div>
      </div>

      <div className="bg-amber-50 border border-amber-200 rounded-xl p-4 mb-6">
        <h3 className="font-medium text-amber-800 mb-2">Có thể do các nguyên nhân sau:</h3>
        <ul className="text-sm text-amber-700 space-y-1">
          <li>• Số dư tài khoản không đủ</li>
          <li>• Thông tin thẻ không chính xác</li>
          <li>• Thẻ đã hết hạn hoặc bị khóa</li>
          <li>• Vượt quá giới hạn giao dịch</li>
        </ul>
      </div>

      <div className="space-y-3">
        <button
          onClick={handleGoBack}
          className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium py-3 px-4 rounded-xl transition-colors duration-200 flex items-center justify-center space-x-2"
        >
          <ArrowLeft className="w-5 h-5" />
          <span>Quay lại</span>
        </button>

        <button
          onClick={() => setPaymentStatus('success')}
          className="w-full bg-green-100 hover:bg-green-200 text-green-700 font-medium py-3 px-4 rounded-xl transition-colors duration-200"
        >
          Xem trạng thái thành công
        </button>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center justify-center p-4">
      <div className="w-full max-w-lg">
        {paymentStatus === 'success' ? successContent : failedContent}
      </div>
    </div>
  );
};

export default PaymentStautsVnpay;

