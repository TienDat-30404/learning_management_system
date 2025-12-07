

'use client'

import React, { useState } from 'react';
import { useGetAllCourse } from '@/hooks/useCourses';
import { Course } from '@/types/course';
import Link from 'next/link';
import { CourseCard } from '@/components/ui/course/CourseCard';


interface LocalQueryParams {
    page: number;
    size: number;
    filter?: 'featured' | 'continued-learning';
}

interface FeaturedCoursesProps {
    dataFilter: 'featured' | 'continued-learning';
    showPreview?: boolean;
    title?: string;
    subtitle?: string;
    maxCourses?: number;
}

const FeaturedCourses: React.FC<FeaturedCoursesProps> = ({
    // dataFilter,
    title = "Khóa Học Nổi Bật",
    subtitle = "Những khóa học được yêu thích nhất từ cộng đồng học viên"
}) => {

    const [page, setPage] = useState<number>(0);
    const size = 4;

    const params: LocalQueryParams = { page, size };

    const { data: coursess, isLoading } = useGetAllCourse(params);

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


    // ... (Tính toán tổng trang, v.v.)
    const totalPages = coursess?.data?.totalPages || 0;
    const isFirstPage = page === 0;
    const isLastPage = page >= totalPages - 1;
    const courses = coursess?.data?.content || [];


    // Tối ưu UI khi loading
    if (isLoading) {
        return (
            <section className="py-16 bg-white text-center">
                <p className="text-xl text-blue-600">Đang tải khóa học...</p>
            </section>
        );
    }

    return (
        <section className="py-16 bg-white">
            <div className="container mx-auto px-4">
                <div className="text-center mb-12">
                    <h2 className="text-4xl font-bold text-gray-900 mb-4">{title}</h2>
                    <p className="text-xl text-gray-600 max-w-2xl mx-auto">{subtitle}</p>
                </div>

                {/* Courses Grid (giữ nguyên logic render) */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 mb-12">
                    {courses.map((course: Course) => (
                        <CourseCard 
                            key={course.id} 
                            course={course}
                        />
                    ))}
                </div>

                {/* Phân trang (Đã thêm) */}
                {totalPages > 1 && (
                    <div className="flex justify-center items-center space-x-4 mb-12">
                        <button
                            onClick={() => setPage(prev => prev - 1)}
                            disabled={isFirstPage || isLoading}
                            className="px-4 py-2 border rounded-lg disabled:opacity-50"
                        >
                            Trang Trước
                        </button>
                        <span className="text-lg">
                            Trang {page + 1} / {totalPages}
                        </span>
                        <button
                            onClick={() => setPage(prev => prev + 1)}
                            disabled={isLastPage || isLoading}
                            className="px-4 py-2 border rounded-lg disabled:opacity-50"
                        >
                            Trang Sau
                        </button>
                    </div>
                )}

                {/* View All Button (giữ nguyên) */}
                {/* ... */}
            </div>
        </section>
    );
};

export default FeaturedCourses;




