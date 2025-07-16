'use client'

import React, { useState } from 'react';
import {
  Shield,
  CheckCircle,
  ArrowLeft,
  Lock,
} from 'lucide-react';
import { useGetDetailCouse } from '@/hooks/useCourses';
import { useParams, useSearchParams } from 'next/navigation';
import { useGetAllPaymentMethods } from '@/hooks/usePaymentMethods';
import { useProcessPaymentVnpay } from '@/hooks/usePayments';
import { PaymentMethod } from '@/types/paymentMethod';
import { formatPrice } from '@/utils/formatters';


const CoursePaymentPage: React.FC = () => {
  const searchParams = useSearchParams();
  const courseId = searchParams.get('courseId');
  const { data: paymentMethods, isLoading, error } = useGetAllPaymentMethods();

  const { mutate, isError, isSuccess, data } = useProcessPaymentVnpay();



  const { data: detailCourse } = useGetDetailCouse(Number(courseId))
  const [selectedPayment, setSelectedPayment] = useState<number>(paymentMethods?.data?.content?.[0]?.id);
  const [isProcessing, setIsProcessing] = useState(false);
  const [cardNumber, setCardNumber] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [cvv, setCvv] = useState('');
  const [cardName, setCardName] = useState('');


  const amount = detailCourse?.data?.discount?.value === null ? detailCourse?.data?.price :
    (detailCourse?.data?.discount?.discountType === "PERCENT" ?
      detailCourse?.data?.price * (1 - detailCourse?.data?.discount?.value / 100) :
      detailCourse?.data?.price - detailCourse?.data?.discount?.value
    )

  const handleSubmitPayment = () => {


    const paymentBody = {
      amount: amount,
      content: `Thanh toán khóa học ${detailCourse?.data?.title}`
    };
    mutate(paymentBody)
    localStorage.setItem('informationPayment', JSON.stringify({
      courseId: courseId,
      amount: amount,
      paymentMethodId: selectedPayment
    }))
  };


  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 py-4">
          <div className="flex items-center gap-4">
            <button className="p-2 hover:bg-gray-100 rounded-full transition-colors">
              <ArrowLeft className="w-5 h-5" />
            </button>
            <h1 className="text-xl font-semibold text-gray-900">Thanh toán khóa học</h1>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="grid lg:grid-cols-3 gap-8">
          {/* Course Info */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-2xl shadow-xl p-6 sticky top-8">
              <div className="relative mb-4">
                <img src={detailCourse?.data?.image} />
                {detailCourse?.data?.discount?.value != null && (

                  <div className="absolute -top-2 -right-2 bg-red-500 text-white px-3 py-1 rounded-full text-sm font-semibold">
                    {detailCourse?.data?.discount?.discountType === "PERCENT" ?
                      "-" + detailCourse?.data?.discount?.value + "%" :
                      "-" + (detailCourse?.data?.discount?.value).toLocaleString('vi-VN') + "đ"

                    }
                  </div>
                )}
              </div>

              <h2 className="text-lg font-bold text-gray-900 mb-2 line-clamp-2">
                {detailCourse?.data?.title}
              </h2>

              <p className="text-gray-600 mb-4">Giảng viên: {detailCourse?.data?.user?.fullName}</p>

              <div className="mb-4 text-sm text-gray-600">
                <div className="flex items-center w-full space-x-1">
                  <p className='text-black'>Mô tả : </p>
                  <p className="text-gray-600">{detailCourse?.data?.description}</p>
                </div>

                <div className="flex items-center gap-1">
                  <p className="text-gray-600 mb-4">Thể loại: {detailCourse?.data?.category?.name}</p>
                </div>
                
              </div>

              <div className="border-t pt-4">
                <h3 className="font-semibold text-gray-900 mb-3">Bạn sẽ nhận được:</h3>
                <ul className="space-y-2">
                  {detailCourse?.data?.learningOutcomes?.split('.').map((feature : string, index : number) => (
                    <li key={index} className="flex items-start gap-2 text-sm text-gray-700">
                      <CheckCircle className="w-4 h-4 text-green-500 mt-0.5 flex-shrink-0" />
                      {feature}
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>

          {/* Payment Form */}
          <div className="lg:col-span-2">
            <div className="bg-white rounded-2xl shadow-xl overflow-hidden">
              {/* Price Summary */}
              <div className="bg-gradient-to-r from-blue-600 to-purple-600 text-white p-6">
                <h2 className="text-2xl font-bold mb-4">Tổng quan thanh toán</h2>
                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <span>Giá gốc:</span>
                    <span className="line-through text-blue-200">{formatPrice(detailCourse?.data?.price)}</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span>Giảm giá:</span>
                    <span className="text-green-300">
                      {detailCourse?.data?.discount?.value != null ? (
                        detailCourse?.data?.discount?.discountType === "PERCENT" ?
                          (detailCourse?.data?.price * detailCourse?.data?.discount?.value / 100).toLocaleString('vi-VN') :
                          (detailCourse?.data?.discount?.value).toLocaleString('vi-VN')
                      ) :
                        "0"
                      }
                    </span>
                  </div>
                  <div className="border-t border-white/20 pt-2">
                    <div className="flex justify-between items-center text-xl font-bold">
                      <span>Tổng cộng:</span>
                      <span>
                        {detailCourse?.data?.discount?.value != null ? (

                          detailCourse?.data?.discount?.discountType === "PERCENT" ?
                            detailCourse?.data?.price * (1 - detailCourse?.data?.discount?.value / 100) :
                            detailCourse?.data?.price - detailCourse?.data?.discount?.value
                        ) : detailCourse?.data?.price
                        }
                      </span>
                    </div>
                  </div>
                </div>
              </div>

              <div className="p-6">
                {/* Payment Methods */}
                <div className="mb-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Chọn phương thức thanh toán</h3>
                  <div className="grid gap-3">
                    {paymentMethods?.data?.content?.map((method: PaymentMethod) => (
                      <label
                        key={method.id}
                        className={`relative flex items-center p-4 rounded-xl border-2 cursor-pointer transition-all ${selectedPayment === Number(method.id)
                          ? 'border-blue-500 bg-blue-50'
                          : 'border-gray-200 hover:border-gray-300'
                          }`}
                      >
                        <input
                          type="radio"
                          name="payment"
                          value={method.id}
                          checked={selectedPayment === Number(method.id)}
                          onChange={(e) => setSelectedPayment(Number(e.target.value))}
                          className="sr-only"
                        />
                        <div className="flex items-center gap-3">
                          <div className={`p-2 rounded-full ${selectedPayment === Number(method.id) ? 'bg-blue-500 text-white' : 'bg-gray-100 text-gray-600'
                            }`}>
                            {/* {method.icon} */}
                          </div>
                          <div>
                            <div className="font-medium text-gray-900">{method.name}</div>
                          </div>
                        </div>
                        {selectedPayment === Number(method.id) && (
                          <CheckCircle className="w-5 h-5 text-blue-500 ml-auto" />
                        )}
                      </label>
                    ))}
                  </div>
                </div>

                {/* Security Notice */}
                <div className="bg-gray-50 rounded-xl p-4 my-6">
                  <div className="flex items-center gap-3">
                    <Shield className="w-5 h-5 text-green-500" />
                    <div>
                      <h4 className="font-medium text-gray-900">Thanh toán bảo mật</h4>
                      <p className="text-sm text-gray-600">
                        Thông tin thanh toán của bạn được mã hóa và bảo vệ bằng SSL 256-bit
                      </p>
                    </div>
                  </div>
                </div>

                {/* Payment Button */}
                <button
                  onClick={handleSubmitPayment}
                  disabled={isProcessing}
                  className="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white py-4 px-6 rounded-xl font-semibold text-lg hover:from-blue-700 hover:to-purple-700 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
                >
                  {isProcessing ? (
                    <div className="flex items-center justify-center gap-2">
                      <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                      Đang xử lý...
                    </div>
                  ) : (
                    <div className="flex items-center justify-center gap-2">
                      <Lock className="w-5 h-5" />
                      Thanh toán {formatPrice(amount)}
                    </div>
                  )}
                </button>

                <p className="text-center text-sm text-gray-600 mt-4">
                  Bằng cách thanh toán, bạn đồng ý với{' '}
                  <a href="#" className="text-blue-600 hover:underline">Điều khoản dịch vụ</a> và{' '}
                  <a href="#" className="text-blue-600 hover:underline">Chính sách bảo mật</a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoursePaymentPage;