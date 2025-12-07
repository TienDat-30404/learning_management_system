'use client'
import { useGetCourseProgressOfUser } from "@/hooks/useEnrollments";
import { Enrollment } from "@/types/enrollment";
import { Award, Clock, PlayCircle } from "lucide-react";
import { useState } from "react";
import { useRouter } from "next/navigation";

export default function CourseProgress() {
    const router = useRouter();
    const [page, setPage] = useState<number>(0)
    const { data: enrollments, isLoading, error } = useGetCourseProgressOfUser({
        page: page,
        size: 4
    });


    return (
        <div className="bg-white rounded-lg shadow">
            <div className="px-6 py-4 border-b border-gray-200">
                <h3 className="text-lg font-semibold text-gray-900">Khóa học của tôi </h3>
            </div>
            <div className="p-6">
                <div className="space-y-4">
                    {enrollments?.data?.content?.map((enrollment: Enrollment) => (
                        <div key={enrollment?.id} className="border border-gray-200 rounded-lg p-4">
                            <div className="flex justify-between items-start mb-3">
                                <div>
                                    <h4 className="font-medium text-gray-900">{enrollment?.course?.title}</h4>
                                    <p className="text-sm text-gray-600">Giảng viên: {enrollment?.user?.fullName}</p>
                                </div>
                                <span className={`px-2 py-1 rounded-full text-xs font-medium ${enrollment?.progress === 100
                                    ? 'bg-green-100 text-green-800'
                                    : 'bg-blue-100 text-blue-800'
                                    }`}>
                                    {enrollment?.progress}%
                                </span>
                            </div>

                            <div className="mb-3">
                                <div className="flex justify-between text-sm text-gray-600 mb-1">
                                    {/* <span>Tiến độ: {course.completedLessons}/{course.totalLessons} bài học</span> */}
                                    <span>{enrollment?.progress}%</span>
                                </div>
                                <div className="w-full bg-gray-200 rounded-full h-2">
                                    <div
                                        className="bg-blue-600 h-2 rounded-full transition-all duration-300"
                                        style={{ width: `${enrollment.progress}%` }}
                                    ></div>
                                </div>
                            </div>

                            <div className="flex justify-between items-center">
                                {/* <div className="flex items-center space-x-4 text-sm text-gray-600">
                                    {course.nextDeadline && (
                                        <span className="flex items-center">
                                            <Clock className="w-4 h-4 mr-1" />
                                            Deadline: {new Date(course.nextDeadline).toLocaleDateString('vi-VN')}
                                        </span>
                                    )}
                                    {course.grade && (
                                        <span className="flex items-center">
                                            <Award className="w-4 h-4 mr-1" />
                                            Điểm: {course.grade}
                                        </span>
                                    )}
                                </div> */}
                                <button
                                    onClick={() => router.push(`/learn/${enrollment?.course?.id}`)}
                                    className="flex items-center space-x-2 px-3 py-1 text-sm text-blue-600 hover:text-blue-800 transition-colors cursor-pointer">
                                    <PlayCircle className="w-4 h-4" />
                                    <span>Tiếp tục học</span>
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}