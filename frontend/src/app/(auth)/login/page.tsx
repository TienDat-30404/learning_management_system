'use client'

import React, { useState } from 'react';
import Link from 'next/link';
import {
  Eye,
  EyeOff,
  Mail,
  Lock,
  BookOpen,
  ArrowRight,
  CheckCircle,
} from 'lucide-react';



import InputWithIcon from '@/components/form/InputWithIcon';
import { LoginFormData, ValidationErrorsLogin } from '@/types/auth';
import { login } from '@/services/auth/auth';
import { useRouter } from 'next/navigation';

const LoginPage = () => {
  const router = useRouter()
  const [formData, setFormData] = useState<LoginFormData>({
    userName: '',
    password: ''
  });

  const [showPassword, setShowPassword] = useState<boolean>(false)
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [errors, setErrors] = useState<ValidationErrorsLogin>({});
  const [loginStatus, setLoginStatus] = useState<'idle' | 'success' | 'error'>('idle');



  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));

    if (errors[name as keyof ValidationErrorsLogin]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined,
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      setErrors({});
      setIsLoading(true);
      setLoginStatus('idle')

      const response = await login({
        userName: formData.userName,
        password: formData.password
      });


      if (response.status === 200) {
        console.log("fff", response)
        console.log("accessToken", response.data.data.accessToken)
        localStorage.setItem('accessToken', response.data.data.accessToken);
        setLoginStatus('success');
        router.push("/")
      }
    }
    catch (error : any) {
      
      if(error.response && error.response.data && error.response.data.errors) {
        const apiErrors = error.response?.data?.errors;

        if (apiErrors) {
          const newErrors: ValidationErrorsLogin = {};
          if (apiErrors.userName) newErrors.userName = apiErrors.userName;
          if (apiErrors.password) newErrors.password = apiErrors.password;
          setErrors(newErrors);
        }
        setLoginStatus('error');
      }


      else {
        setErrors(prev => ({
          ...prev,
          userName: 'Lỗi mạng hoặc server, vui lòng thử lại',
        }));
        setLoginStatus('error');
      }
    }
    finally {
      setIsLoading(false);
    }

  };

  const handleSocialLogin = (provider: string) => {
    console.log(`Login with ${provider}`);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center p-4 relative overflow-hidden">
      {/* Animated Background Elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute -top-40 -right-40 w-80 h-80 bg-gradient-to-r from-blue-400 to-purple-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse"></div>
        <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-gradient-to-r from-purple-400 to-pink-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse animation-delay-2000"></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-60 h-60 bg-gradient-to-r from-indigo-400 to-blue-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse animation-delay-4000"></div>
      </div>

      {/* Main Container */}
      <div className="relative z-10 w-full max-w-md">
        {/* Login Card */}
        <div className="bg-white/80 backdrop-blur-xl rounded-3xl shadow-2xl border border-white/20 p-8 transform transition-all duration-300 hover:shadow-3xl">
          {/* Logo & Header */}
          <div className="text-center mb-8">
            <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-600 to-purple-600 rounded-2xl mb-4 shadow-lg">
              <BookOpen className="w-8 h-8 text-white" />
            </div>
            <h1 className="text-3xl font-bold text-gray-800 mb-2">EduLearn</h1>
            <p className="text-gray-600">Chào mừng trở lại! Đăng nhập để tiếp tục học tập</p>
          </div>

          {/* Status Messages */}
          {loginStatus === 'success' && (
            <div className="mb-6 p-4 bg-green-50 border border-green-200 rounded-xl flex items-center space-x-3">
              <CheckCircle className="w-5 h-5 text-green-600" />
              <span className="text-green-700 font-medium">Đăng nhập thành công!</span>
            </div>
          )}

          {/* Login Form */}
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Username Field */}
            <InputWithIcon
              label="Username"
              name="userName"
              type="text"
              value={formData.userName}
              onChange={handleInputChange}
              placeholder="abc"
              icon={<Mail className="h-5 w-5 text-gray-400" />}
              error={errors.userName}
            />

            {/* Password Field */}
            <InputWithIcon
              label="Mật khẩu"
              name="password"
              type={showPassword ? 'text' : 'password'}
              value={formData.password}
              onChange={handleInputChange}
              placeholder="123456"
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

            {/* Remember Me & Forgot Password */}
            <div className="flex items-center justify-between">
              <label className="flex items-center space-x-2 cursor-pointer">
                <input
                  type="checkbox"
                  name="rememberMe"
                  // checked={formData.rememberMe}
                  onChange={handleInputChange}
                  className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                />
                <span className="text-sm text-gray-600">Ghi nhớ đăng nhập</span>
              </label>
              <button
                type="button"
                className="text-sm text-blue-600 hover:text-blue-800 font-medium transition-colors"
              >
                Quên mật khẩu?
              </button>
            </div>

            {/* Login Button */}
            <button
              type="submit"
              disabled={isLoading}
              className="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white py-3 px-4 rounded-xl font-medium hover:from-blue-700 hover:to-purple-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none flex items-center justify-center space-x-2"
            >
              {isLoading ? (
                <>
                  <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                  <span>Đang đăng nhập...</span>
                </>
              ) : (
                <>
                  <span>Đăng nhập</span>
                  <ArrowRight className="w-5 h-5" />
                </>
              )}
            </button>
          </form>

          {/* Divider */}
          <div className="my-8 flex items-center">
            <div className="flex-1 border-t border-gray-200"></div>
            <span className="px-4 text-sm text-gray-500 bg-white">Hoặc đăng nhập với</span>
            <div className="flex-1 border-t border-gray-200"></div>
          </div>

          {/* Social Login */}
          <div className="grid grid-cols-2 gap-4">
            <button
              onClick={() => handleSocialLogin('google')}
              className="flex items-center justify-center space-x-2 py-3 px-4 border border-gray-200 rounded-xl hover:bg-gray-50 transition-colors duration-200 hover:border-gray-300"
            >
              <span className="text-sm font-medium text-gray-700">Google</span>
            </button>
            <button
              onClick={() => handleSocialLogin('github')}
              className="flex items-center justify-center space-x-2 py-3 px-4 border border-gray-200 rounded-xl hover:bg-gray-50 transition-colors duration-200 hover:border-gray-300"
            >
              <span className="text-sm font-medium text-gray-700">Github</span>
            </button>
          </div>

          {/* Sign Up Link */}
          <div className="mt-8 text-center">
            <p className="text-sm text-gray-600">
              Chưa có tài khoản?{' '}
              <Link href="/sign-up" className="text-blue-600 font-semibold hover:underline">
                Đăng ký
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
