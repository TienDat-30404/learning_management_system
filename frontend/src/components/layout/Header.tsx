'use client';

import Link from 'next/link';
import React, { useState } from 'react';
import { Search, Bell, User, BookOpen, Menu, X, LogOut, Settings, UserCircle, ChevronDown } from 'lucide-react';
import { useDispatch, useSelector, UseSelector } from 'react-redux';
import { useAppSelector } from '@/hooks/useRedux';
import { logout } from '@/store/userSlice';

export default function Header() {
    const dispatch = useDispatch()
    const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
    const {isAuthenticated, userInfo} = useAppSelector(state => state.user)
    console.log("isAuthenticated", isAuthenticated)
    console.log("usserInfo", userInfo)

    const handleLogout = () => {
        console.log("Đăng xuất");
        dispatch(logout());
    };

    return (
        <header className="bg-white shadow-sm border-b">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    {/* Logo */}
                    <div className="flex items-center">
                        <div className="flex-shrink-0 flex items-center">
                            <BookOpen className="w-8 h-8 text-blue-600" />
                            <span className="ml-2 text-xl font-bold text-gray-900">EduLearn</span>
                        </div>
                    </div>

                    {/* Desktop Navigation */}
                    <nav className="hidden md:flex space-x-8">
                        <a href="#" className="text-blue-600 font-medium">Trang chủ</a>
                        <a href="#" className="text-gray-700 hover:text-blue-600">Khóa học</a>
                        <a href="#" className="text-gray-700 hover:text-blue-600">Lộ trình</a>
                        <a href="#" className="text-gray-700 hover:text-blue-600">Thành tích</a>
                    </nav>

                    {/* Search & Profile */}
                    <div className="hidden md:flex items-center space-x-4 ">
                        <div className="relative">
                            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
                            <input
                                type="text"
                                placeholder="Tìm kiếm khóa học..."
                                className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                        </div>
                        <button className="p-2 text-gray-400 hover:text-gray-600">
                            <Bell className="w-5 h-5" />
                        </button>

                        {isAuthenticated ? (
                            <div className="relative">
                                <div
                                    className="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100 cursor-pointer"
                                    onMouseEnter={() => setIsDropdownOpen(true)}
                                    onMouseLeave={() => setIsDropdownOpen(false)}
                                >
                                    <User className="w-5 h-5 text-gray-600" />
                                    <span className="text-sm font-medium text-gray-700">
                                        {userInfo?.fullName || 'Người dùng'}
                                    </span>
                                    <ChevronDown size = {11} />
                                </div>

                                {/* Dropdown Menu */}
                                {isDropdownOpen && (
                                    <div 
                                        className="absolute right-0 w-56 bg-white border border-gray-200 rounded-lg shadow-lg z-50"
                                        onMouseEnter={() => setIsDropdownOpen(true)}
                                        onMouseLeave={() => setIsDropdownOpen(false)}
                                    >
                                        <div className="py-2">
                                            {/* User Info Section */}
                                            <div className="px-4 py-3 border-b border-gray-100">
                                                <div className="flex items-center space-x-3">
                                                    <div className="w-10 h-10 bg-blue-100 rounded-full flex items-center justify-center">
                                                        <UserCircle className="w-6 h-6 text-blue-600" />
                                                    </div>
                                                    <div>
                                                        <p className="text-sm font-medium text-gray-900">
                                                            {userInfo?.fullName || 'Người dùng'}
                                                        </p>
                                                        <p className="text-xs text-gray-500">
                                                            {userInfo?.email || 'user@example.com'}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>

                                            {/* Menu Items */}
                                            <div className="py-1">
                                                <Link 
                                                    href="/profile" 
                                                    className="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900"
                                                >
                                                    <UserCircle className="w-4 h-4 mr-3" />
                                                    Thông tin cá nhân
                                                </Link>
                                                <Link 
                                                    href="/settings" 
                                                    className="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900"
                                                >
                                                    <Settings className="w-4 h-4 mr-3" />
                                                    Cài đặt
                                                </Link>
                                                <hr className="my-1 border-gray-100" />
                                                <button
                                                    onClick={handleLogout}
                                                    className="flex items-center w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50 hover:text-red-700"
                                                >
                                                    <LogOut className="w-4 h-4 mr-3" />
                                                    Đăng xuất
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                )}
                            </div>
                        ) : (
                            <Link href="/login" className="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100">
                                <User className="w-5 h-5 text-gray-600" />
                                <span className="text-sm font-medium text-gray-700">Đăng nhập</span>
                            </Link>
                        )}
                    </div>

                  
                </div>
            </div>

          
        </header>
    )
}