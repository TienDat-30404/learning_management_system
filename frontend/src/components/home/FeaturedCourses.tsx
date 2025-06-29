import React, { useState } from 'react';
import Link from 'next/link';
import { useGetAllCourse } from '@/hooks/useCourses';
import { Course } from '@/types/courses';


interface FeaturedCoursesProps {
    showPreview?: boolean; // Để hiển thị preview cho người chưa đăng nhập
    title?: string;
    subtitle?: string;
    maxCourses?: number;
}

const FeaturedCourses: React.FC<FeaturedCoursesProps> = ({
    showPreview = false,
    title = "Khóa Học Nổi Bật",
    subtitle = "Những khóa học được yêu thích nhất từ cộng đồng học viên",
    maxCourses = 8
}) => {



    const renderStars = (rating: number) => {
        return Array.from({ length: 5 }, (_, index) => (
            <svg
                key={index}
                className={`w-4 h-4 ${index < Math.floor(rating) ? 'text-yellow-400' : 'text-gray-300'}`}
                fill="currentColor"
                viewBox="0 0 20 20"
            >
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
            </svg>
        ));
    };

    const [page, setPage] = useState<number>(0);
    const { data: coursess, isLoading, error } = useGetAllCourse({
        page: Number(page),
        size: 4
    })
    console.log("coursess", coursess)

    return (
        <section className="py-16 bg-white">
            <div className="container mx-auto px-4">
                {/* Header */}
                <div className="text-center mb-12">
                    <h2 className="text-4xl font-bold text-gray-900 mb-4">
                        {title}
                    </h2>
                    <p className="text-xl text-gray-600 max-w-2xl mx-auto">
                        {subtitle}
                    </p>
                </div>

                {/* Courses Grid */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 mb-12">
                    {coursess?.data?.content.map((course: Course) => (
                        <div key={course.id} className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 overflow-hidden">
                            {/* Course Image */}
                            <div className="relative">
                                <img
                                    src={course?.image}
                                    alt={course?.title}
                                    className="w-full h-48 object-cover"
                                />

                                <div className="absolute top-3 left-3 flex gap-2">
                                    {/* {course.isBestseller && ( */}
                                    <span className="bg-orange-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                                        Bestseller
                                    </span>
                                    {/* )} */}
                                    {/* {course.isNew && ( */}
                                    <span className="bg-green-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                                        Mới
                                    </span>
                                    {/* )} */}
                                </div>

                                {/* Overlay for preview mode */}
                                {showPreview && (
                                    <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 hover:opacity-100 transition-opacity duration-200">
                                        <Link
                                            href="/register"
                                            className="bg-white text-gray-900 px-4 py-2 rounded-lg font-medium hover:bg-gray-100 transition-colors"
                                        >
                                            Đăng nhập để xem
                                        </Link>
                                    </div>
                                )}
                            </div>

                            <div className="p-6">
                                <div className="flex justify-between items-center mb-2">
                                    <span className="text-blue-600 text-sm font-medium">{course?.category?.name}</span>
                                    <span className="text-gray-500 text-sm">
                                        {/* {course.level} */}
                                        Trung cấp
                                    </span>
                                </div>

                                <h3 className="font-semibold text-gray-900 mb-2 line-clamp-2 min-h-[3rem]">
                                    {course.title}
                                </h3>

                                <p className="text-gray-600 text-sm mb-3">{course?.user?.fullName}</p>

                                <div className="flex items-center mb-3">
                                    <div className="flex items-center mr-2">
                                        {renderStars(4.5)}
                                    </div>
                                    <span className="text-sm font-medium text-gray-900 mr-1">5</span>
                                    <span className="text-sm text-gray-500">(20 học viên)</span>
                                </div>

                                <div className="flex items-center text-sm text-gray-500 mb-4">
                                    <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                                    </svg>
                                    {/* {course.duration} */}
                                    20 giờ
                                </div>

                                <div className="flex items-center justify-between">
                                    <div className="flex items-center">
                                        <span className="text-lg font-bold text-gray-900">{course.price}</span>
                                        {/* {course.originalPrice && ( */}
                                        <span className="text-sm text-gray-500 line-through ml-2">
                                            {/* {course.originalPrice} */}
                                            200.000đ
                                        </span>
                                        {/* )} */}
                                    </div>

                                    {!showPreview ? (
                                        <Link
                                            href={`/courses/${course.id}`}
                                            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                                        >
                                            Xem chi tiết
                                        </Link>
                                    ) : (
                                        <Link
                                            href="/register"
                                            className="bg-gray-600 hover:bg-gray-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                                        >
                                            Đăng ký
                                        </Link>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                {/* View All Button */}
                <div className="text-center">
                    <Link
                        href="/courses"
                        className="inline-flex items-center px-6 py-3 border border-blue-600 text-blue-600 hover:bg-blue-600 hover:text-white rounded-lg font-medium transition-colors duration-200"
                    >
                        Xem Tất Cả Khóa Học
                        <svg className="ml-2 w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                        </svg>
                    </Link>
                </div>
            </div>
        </section>
    );
};

export default FeaturedCourses;