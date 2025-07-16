'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { Eye, EyeOff, User, Mail, Lock, BookOpen, ArrowRight, Globe, UserRound, Calendar } from 'lucide-react';
import InputWithIcon from '@/components/form/InputWithIcon';
import SelectWithIcon from '@/components/form/Select';

import { RegisterFormData, ValidationErrorsRegister } from '@/types/auth';
import { register } from '@/services/auth/auth';
import { useRouter } from 'next/navigation';
const RegisterPage: React.FC = () => {
  const router = useRouter()
  const [formData, setFormData] = useState<RegisterFormData>({
    userName: '',
    fullName: '',
    email: '',
    password: '',
    gender: '',
    birthDate: '',
    agreeTerms: false
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [errors, setErrors] = useState<ValidationErrorsRegister>({})
  const [isLoading, setIsLoading] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;

    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));

    if (errors[name as keyof ValidationErrorsRegister]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };



  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.agreeTerms) {
      setErrors(prev => ({
        ...prev,
        agreeTerms: 'Bạn cần đồng ý với điều khoản trước khi đăng ký.',
      }));
      return; 
    }
    setIsLoading(true);

    try {
      // Simulate API call
      const response = await register({
        userName: formData.userName,
        fullName: formData.fullName,
        email: formData.email,
        password: formData.password,
        gender: formData.gender,
        birthDate: formData.birthDate,
        agreeTerms: formData.agreeTerms
      })
      console.log(response)
      if(response.status === 201) {
        alert("Đăng ký thành công")
        router.push('/login')
      }

      // Reset form
      setFormData({
        userName: '',
        fullName: '',
        email: '',
        password: '',
        gender: '',
        birthDate: '',
        agreeTerms: false
      });
    } catch (error: any) {
      console.error('Registration error:', error);
      if (error.response && error.response.data && error.response.data.errors) {
        const apiErrors = error.response?.data?.errors;

        if (apiErrors) {
          const newErrors: ValidationErrorsRegister = {};
          if (apiErrors.userName) newErrors.userName = apiErrors.userName;
          if (apiErrors.fullName) newErrors.fullName = apiErrors.fullName;
          if (apiErrors.email) newErrors.email = apiErrors.email;
          if (apiErrors.password) newErrors.password = apiErrors.password;
          if (apiErrors.gender) newErrors.gender = apiErrors.gender;
          if (apiErrors.birthDate) newErrors.birthDate = apiErrors.birthDate;

          setErrors(newErrors);
        }
        // setLoginStatus('error');
      }


      else {
        setErrors(prev => ({
          ...prev,
          userName: 'Lỗi mạng hoặc server, vui lòng thử lại',
        }));
        // setLoginStatus('error');
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        
        <div className="text-center">
          <div className="flex justify-center">
            <div className="bg-indigo-600 p-3 rounded-full">
              <BookOpen className="h-8 w-8 text-white" />
            </div>
          </div>
          <h2 className="mt-6 text-3xl font-bold text-gray-900">
            Tạo tài khoản mới
          </h2>
          <p className="mt-2 text-sm text-gray-600">
            Tham gia cộng đồng học tập trực tuyến
          </p>
        </div>

        {/* Form */}
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="bg-white rounded-2xl shadow-xl p-8 space-y-6">

            <InputWithIcon
              label="Username"
              name="userName"
              type="text"
              value={formData.userName}
              onChange={handleInputChange}
              placeholder="abc"
              icon={<User className="h-5 w-5 text-gray-400" />}
              error={errors.userName}
            />
            {/* Email */}

            <InputWithIcon
              label="Full Name"
              name="fullName"
              type="text"
              value={formData.fullName}
              onChange={handleInputChange}
              placeholder="Nhập họ và tên"
              icon={<User className="h-5 w-5 text-gray-400" />}
              error={errors.fullName}
            />

            {/* Password */}
            <InputWithIcon
              label="Email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="abc"
              icon={<Mail className="h-5 w-5 text-gray-400" />}
              error={errors.email}
            />

            {/* Confirm Password */}
            <InputWithIcon
              label="Mật khẩu"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="abc"
              icon={<Lock className="h-5 w-5 text-gray-400" />}
              error={errors.password}
              extraRightButton={
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="text-gray-400 hover:text-gray-600 transition-colors focus:outline-none"
                  tabIndex={-1}
                >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                </button>
              }
            />

            <InputWithIcon
              label="Ngày sinh"
              name="birthDate"
              type="date"
              value={formData.birthDate}
              onChange={handleInputChange}
              placeholder="abc"
              icon={<Calendar className="h-5 w-5 text-gray-400" />}
              error={errors.birthDate}
            />

            <SelectWithIcon
              label="Giới tính"
              name="gender"
              icon={<UserRound className="w-5 h-5" />}
              value={formData.gender}
              onChange={handleInputChange}
              options={[
                { label: 'Nam', value: 'Nam' },
                { label: 'Nữ', value: 'Nữ' },
                { label: 'Khác', value: 'Khác' },
              ]}
              error={errors.gender}
            />

            {/* Terms Agreement */}
            <div className="flex items-start">
              <div className="flex items-center h-5">
                <input
                  id="agreeTerms"
                  name="agreeTerms"
                  type="checkbox"
                  checked={formData.agreeTerms}
                  onChange={handleInputChange}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
              </div>
              <div className="ml-3 text-sm">
                <label htmlFor="agreeTerms" className="text-gray-700">
                  Tôi đồng ý với{' '}
                  <Link href="/terms" className="text-indigo-600 hover:text-indigo-500 font-medium">
                    Điều khoản sử dụng
                  </Link>{' '}
                  và{' '}
                  <Link href="/privacy" className="text-indigo-600 hover:text-indigo-500 font-medium">
                    Chính sách bảo mật
                  </Link>
                </label>
                {errors.agreeTerms && (
                  <p className="mt-1 text-sm text-red-600">{errors.agreeTerms as string}</p>
                )}
              </div>
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              disabled={isLoading}
              className="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-xl text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
            >
              {isLoading ? (
                <div className="flex items-center">
                  <div className="animate-spin -ml-1 mr-3 h-5 w-5 border-2 border-white border-t-transparent rounded-full"></div>
                  Đang xử lý...
                </div>
              ) : (
                <div className="flex items-center">
                  Tạo tài khoản
                  <ArrowRight className="ml-2 h-4 w-4 group-hover:translate-x-1 transition-transform" />
                </div>
              )}
            </button>
          </div>
        </form>

        {/* Login Link */}
        <div className="text-center">
          <p className="text-sm text-gray-600">
            Đã có tài khoản?{' '}
            <Link
              href="/login"
              className="font-medium text-indigo-600 hover:text-indigo-500 transition-colors"
            >
              Đăng nhập ngay
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;