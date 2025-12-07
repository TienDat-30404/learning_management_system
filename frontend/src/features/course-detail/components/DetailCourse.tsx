'use client'

import React, { useState } from 'react';
import {
    Play,
    Clock,
    Users,
    BookOpen,
    Star,
    Share2,
    Heart,
    CheckCircle,
    FileText,
    Globe,
    MessageSquare,
} from 'lucide-react';
import { useGetDetailCouse } from '@/hooks/useCourses';
import { useParams } from 'next/navigation';
import { ReviewCourse } from '@/features/course-detail/components/ReviewCourse';
import { InformationInstructor } from '@/features/course-detail/components/InformationInstructor';
import { LessonsOfCourse } from '@/features/course-detail/components/LessonsOfCourse';
import { useRouter } from 'next/navigation';

export const CourseDetailsClient: React.FC<{ courseId: number }> = ({ courseId }) => {
    const [activeTab, setActiveTab] = useState<'overview' | 'curriculum' | 'instructor' | 'reviews'>('overview');
    const router = useRouter()

    const { data: detailCourse, isLoading, error } = useGetDetailCouse(Number(courseId))

    const handleEnroll = () => {
        router.push(`/payment?courseId=${courseId}`);
    };

    const formatPrice = (price: number) => {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(price);
    };

    const renderStars = (rating: number) => {
        return Array.from({ length: 5 }, (_, i) => (
            <Star
                key={i}
                className={`w-4 h-4 ${i < rating ? 'text-yellow-400 fill-current' : 'text-gray-300'}`}
            />
        ));
    };


    return (
        <div className="min-h-screen bg-gray-50">

            <div className="bg-white shadow-sm">
                <div className="max-w-7xl mx-auto px-4 py-4">
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                        <span>Trang chủ</span>
                        <span>/</span>
                        <span>{detailCourse?.data?.category?.name}</span>
                        <span>/</span>
                        <span className="text-blue-600">{detailCourse?.data?.title}</span>
                    </div>
                </div>
            </div>

            <div className="max-w-7xl mx-auto px-4 py-8">
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    {/* Main Content */}
                    <div className="lg:col-span-2">

                        <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
                            <div className="flex items-center gap-2 mb-4">
                                <span className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-medium">
                                    {detailCourse?.data?.category?.name}
                                </span>

                            </div>

                            <h1 className="text-3xl font-bold text-gray-900 mb-4">
                                {detailCourse?.data?.title}
                            </h1>

                            <div className="flex flex-wrap items-center gap-6 mb-6">
                                <div className="flex items-center gap-1">
                                    {renderStars(Math.floor(5))}
                                    <span className="ml-2 font-semibold">{2}</span>
                                    <span className="text-gray-500">(200 đánh giá)</span>
                                </div>

                                <div className="flex items-center gap-1 text-gray-600">
                                    <Users className="w-4 h-4" />
                                    <span>200 học viên</span>
                                </div>

                                <div className="flex items-center gap-1 text-gray-600">
                                    <Clock className="w-4 h-4" />
                                    <span>200 giờ</span>
                                </div>

                                <div className="flex items-center gap-1 text-gray-600">
                                    <BookOpen className="w-4 h-4" />
                                    <span>500bài học</span>
                                </div>

                                <div className="flex items-center gap-1 text-gray-600">
                                    <Globe className="w-4 h-4" />
                                    <span>Ngôn ngữ 123</span>
                                </div>
                            </div>

                            <p className="text-gray-700 leading-relaxed">
                                {detailCourse?.data?.description}
                            </p>
                        </div>

                        {/* Tabs */}
                        <div className="bg-white rounded-lg shadow-sm mb-6">
                            <div className="border-b border-gray-200">
                                <nav className="flex space-x-8 px-6">
                                    {[
                                        { id: 'overview', label: 'Tổng quan', icon: BookOpen },
                                        { id: 'curriculum', label: 'Nội dung khóa học', icon: FileText },
                                        { id: 'instructor', label: 'Giảng viên', icon: Users },
                                        { id: 'reviews', label: 'Đánh giá', icon: MessageSquare }
                                    ].map(tab => (
                                        <button
                                            key={tab.id}
                                            onClick={() => setActiveTab(tab.id as any)}
                                            className={`flex items-center gap-2 py-4 px-1 border-b-2 font-medium text-sm ${activeTab === tab.id
                                                ? 'border-blue-500 text-blue-600'
                                                : 'border-transparent text-gray-500 hover:text-gray-700'
                                                }`}
                                        >
                                            <tab.icon className="w-4 h-4" />
                                            {tab.label}
                                        </button>
                                    ))}
                                </nav>
                            </div>

                            <div className="p-6">
                                {activeTab === 'overview' && (
                                    <div className="space-y-6">
                                        <div>
                                            <h3 className="text-xl font-semibold mb-4">Bạn sẽ học được gì?</h3>
                                            <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                                                {detailCourse?.data?.learningOutcomes?.split('.').map((item: string, index: number) => (
                                                    <div key={index} className="flex items-start gap-3">
                                                        <CheckCircle className="w-5 h-5 text-green-500 mt-0.5 flex-shrink-0" />
                                                        <span className="text-gray-700">{item}</span>
                                                    </div>
                                                ))}
                                            </div>
                                        </div>

                                    </div>
                                )}

                                {activeTab === 'curriculum' && (
                                    <div className="space-y-4">
                                        <LessonsOfCourse
                                            courseId={Number(courseId)}
                                        />
                                    </div>
                                )}


                                {activeTab === 'instructor' && (
                                    <InformationInstructor
                                        idUser={detailCourse?.data?.user?.id}
                                    />
                                )}

                                {activeTab === 'reviews' && (
                                    <ReviewCourse
                                        idCourse={Number(courseId)}
                                    />
                                )}

                            </div>
                        </div>
                    </div>

                    {/* Sidebar */}
                    <div className="lg:col-span-1">
                        <div className="sticky top-4">
                            {/* Course Preview */}
                            <div className="bg-white rounded-lg shadow-sm overflow-hidden mb-6">
                                <div className="relative">
                                    <img
                                        src={detailCourse?.data?.image}
                                        className="w-full h-48 object-cover"
                                    />
                                    <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                                        <button className="bg-white bg-opacity-20 backdrop-blur-sm rounded-full p-4 hover:bg-opacity-30 transition-all">
                                            <Play className="w-8 h-8 text-white" />
                                        </button>
                                    </div>
                                </div>

                                <div className="p-6">
                                    <div className="flex items-center gap-2 mb-4">
                                        <span className="text-3xl font-bold text-gray-900">
                                            {
                                                detailCourse?.data?.discount?.value === null ? (

                                                    formatPrice(detailCourse?.data?.price)
                                                ) :

                                                    detailCourse?.data?.discount?.discountType === "PERCENT" ?
                                                        formatPrice(detailCourse?.data?.price * (1 - detailCourse?.data?.discount?.value / 100)) :
                                                        formatPrice(detailCourse?.data?.price - detailCourse?.data?.discount?.value)
                                            }
                                        </span>

                                        {detailCourse?.data?.discount?.value != null && (

                                            <span className="text-lg text-gray-500 line-through">
                                                {formatPrice(detailCourse?.data?.price)}

                                            </span>
                                        )}

                                        {detailCourse?.data?.discount?.value != null && (
                                            <span className="bg-red-100 text-red-800 px-2 py-1 rounded text-sm font-medium">
                                                {detailCourse?.data?.discount?.discountType === "PERCENT" ?
                                                    detailCourse?.data?.discount?.value + "%" :
                                                    detailCourse?.data?.discount?.value + "đ"
                                                }
                                            </span>
                                        )}
                                    </div>

                                    <div className="space-y-3">
                                        <button
                                            onClick={handleEnroll}
                                            className="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors"
                                        >
                                            Đăng ký ngay
                                        </button>
                                        <button className="w-full border border-gray-300 text-gray-700 py-3 rounded-lg font-medium hover:bg-gray-50 transition-colors">
                                            Thêm vào giỏ hàng
                                        </button>
                                    </div>

                                    <div className="flex items-center justify-center gap-4 mt-4 pt-4 border-t border-gray-200">
                                        <button className="flex items-center gap-2 text-gray-600 hover:text-gray-800">
                                            <Heart className="w-4 h-4" />
                                            <span>Yêu thích</span>
                                        </button>
                                        <button className="flex items-center gap-2 text-gray-600 hover:text-gray-800">
                                            <Share2 className="w-4 h-4" />
                                            <span>Chia sẻ</span>
                                        </button>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div >
    );
};

