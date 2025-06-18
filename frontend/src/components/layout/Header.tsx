'use client';

import Link from 'next/link';
import React, { useState } from 'react';
import { Search, Bell, User, BookOpen, Menu, X, } from 'lucide-react';
export default function Header() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);

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
                    <div className="hidden md:flex items-center space-x-4">
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
                        {/* <button className="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100">
                            <User className="w-5 h-5 text-gray-600" />
                            <span className="text-sm font-medium text-gray-700">Nguyễn Văn Nam</span>
                       
                        </button> */}

                        <Link href="/login" className="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100">
                            <User className="w-5 h-5 text-gray-600" />
                            <span className="text-sm font-medium text-gray-700">Đăng nhập</span>
                        </Link>

                    </div>

                    {/* Mobile menu button */}
                    <div className="md:hidden">
                        <button
                            onClick={() => setIsMenuOpen(!isMenuOpen)}
                            className="p-2 text-gray-400 hover:text-gray-600"
                        >
                            {isMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
                        </button>
                    </div>
                </div>
            </div>

            {/* Mobile Navigation */}
            {isMenuOpen && (
                <div className="md:hidden bg-white border-t">
                    <div className="px-4 py-2 space-y-2">
                        <a href="#" className="block py-2 text-blue-600 font-medium">Trang chủ</a>
                        <a href="#" className="block py-2 text-gray-700">Khóa học</a>
                        <a href="#" className="block py-2 text-gray-700">Lộ trình</a>
                        <a href="#" className="block py-2 text-gray-700">Thành tích</a>
                    </div>
                </div>
            )}
        </header>
    )
}
