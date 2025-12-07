import { Course } from '@/types/course'
import Link from 'next/link'
import React from 'react'

export const CourseCard: React.FC<{course: Course}> = ({course}) => {
    return (
        <div key={course.id} className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 overflow-hidden">
            <div className="relative">
                <img
                    src={course?.image}
                    alt={course?.title}
                    className="w-full h-48 object-cover"
                />

                <div className="absolute top-3 left-3 flex gap-2">
                    <span className="bg-orange-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                        Bestseller
                    </span>

                    <span className="bg-green-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                        Mới
                    </span>
                </div>
            </div>

            <div className="p-6">
                <div className="flex justify-between items-center mb-2">
                    <span className="text-blue-600 text-sm font-medium">{course?.category?.name}</span>
                    <span className="text-gray-500 text-sm">
                        Trung cấp
                    </span>
                </div>

                <h3 className="font-semibold text-gray-900 mb-2 line-clamp-2 min-h-[3rem]">
                    {course.title}
                </h3>

                <p className="text-gray-600 text-sm mb-3">{course?.user?.fullName}</p>

                <div className="flex items-center mb-3">
                    <div className="flex items-center mr-2">
                        {/* {renderStars(4.5)} */}
                    </div>
                    <span className="text-sm font-medium text-gray-900 mr-1">5</span>
                    <span className="text-sm text-gray-500">(20 học viên)</span>
                </div>

                <div className="flex items-center text-sm text-gray-500 mb-4">
                    <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    20 giờ
                </div>

                <div className="flex items-center justify-between">
                    <div className="flex items-center">

                        <span className="text-lg font-bold text-gray-900 mr-2">
                            {course?.discount?.value === null ? (
                                course?.price
                            ) :
                                course?.discount?.discountType === "PERCENT" ?
                                    course?.price * (1 - course?.discount?.value / 100) :
                                    course?.price - course?.discount?.value
                            }
                        </span>
                        {course?.discount?.value != null && (
                            <div>
                                <span>-</span>
                                <span className="text-sm text-gray-500 line-through">
                                    {
                                        course?.discount?.discountType === "PERCENT" ?
                                            course?.discount?.value + "%" :
                                            (course?.price).toLocaleString('vi-VN') + "đ"
                                    }
                                </span>
                            </div>
                        )}
                    </div>


                    <Link
                        href={`/course/${course.id}`}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                    >
                        Xem chi tiết
                    </Link>

                </div>
            </div>
        </div>
    )
}
