import { useListStudentsOfTeacher } from '@/hooks/useTeacher'
import { Enrollment } from '@/types/enrollment'
import { Eye, MessageCircle, User } from 'lucide-react'
import React, { useState } from 'react'

export default function StudentContent() {
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(4);
    const [userId, setUserId] = useState<number | null>(null)
    const { data: students } = useListStudentsOfTeacher({
        page: Number(page),
        size: Number(size),
        userId: userId
    })

    const { data: allStudents } = useListStudentsOfTeacher({
        page: Number(page),
        size: Number(size)
    })

    const uniqueStudents = allStudents?.data?.content.reduce((acc : any, currentStudent : Enrollment) => {
        const isDuplicate = acc.some((student : Enrollment) => student?.user?.id === currentStudent?.user?.id);
        if (!isDuplicate) {
            acc.push(currentStudent);
        }

        return acc;
    }, []); 
    console.log("uniqueStudnet" , uniqueStudents)



    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold text-gray-900">Quản lý Học viên</h2>
                <div className="flex space-x-2">
                    <select
                        className="border border-gray-300 rounded-lg px-3 py-2 text-sm"
                        onChange={(e) => setUserId(Number(e.target.value))}
                    >
                        <option value="null">Tất cả học viên</option>
                        {uniqueStudents?.map((student: Enrollment) => (
                            <option
                                key={student?.user?.id}
                                value={student?.user?.id}
                            >
                                {student?.user?.fullName}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="bg-white rounded-lg shadow-sm border overflow-hidden">
                <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Học viên
                                </th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Khóa học
                                </th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Tiến độ
                                </th>

                                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Thao tác
                                </th>
                            </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                            {students?.data?.content?.map((student: Enrollment) => (
                                <tr key={student.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 whitespace-nowrap">
                                        <div className="flex items-center">
                                            <div className="flex-shrink-0 h-10 w-10">
                                                <div className="h-10 w-10 rounded-full bg-gray-300 flex items-center justify-center">
                                                    <User className="w-5 h-5 text-gray-600" />
                                                </div>
                                            </div>
                                            <div className="ml-4">
                                                <div className="text-sm font-medium text-gray-900">{student?.user?.fullName}</div>
                                                <div className="text-sm text-gray-500">{student?.user?.email}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                        {student?.course?.title}
                                    </td>
                                    <td className="px-6 py-4 whitespace-nowrap">
                                        <div className="flex items-center">
                                            <div className="flex-1 mr-4">
                                                <div className="w-full bg-gray-200 rounded-full h-2">
                                                    <div
                                                        className="bg-green-600 h-2 rounded-full"
                                                        style={{ width: `${student.progress}%` }}
                                                    ></div>
                                                </div>
                                            </div>
                                            <span className="text-sm text-gray-900">{student.progress}%</span>
                                        </div>
                                    </td>
                                    {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                        {student.lastActive}
                                    </td> */}
                                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                        <button className="text-blue-600 hover:text-blue-900 mr-3">
                                            <MessageCircle className="w-4 h-4" />
                                        </button>
                                        <button className="text-green-600 hover:text-green-900">
                                            <Eye className="w-4 h-4" />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}
