'use client'

import { useUpdateUser } from "@/hooks/useUsers";
import { AppDispatch, RootState } from "@/store";
import { BookOpen, Calendar, CheckCircle, Edit, GraduationCap, Mail, MapPin, Mars, Phone, Save, TrendingUp, X } from "lucide-react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export default function OverviewProfile() {
    const [isEditing, setIsEditing] = useState(false);
    const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user)
    const dispatch = useDispatch<AppDispatch>()
    const { mutate, isError, isSuccess, data } = useUpdateUser();

    const [studentData, setStudentData] = useState({
        fullName: userInfo?.fullName,
        email: userInfo?.email,
        phone: '0123456789',
        birthDate: userInfo?.birthDate,
        gender: userInfo?.gender,
        studentId: 'SV001234',
        enrollmentDate: '2024-01-15',
        major: 'Công nghệ thông tin',
        year: 'Năm 3',
        gpa: 3.75,
        avatar: '/api/placeholder/150/150'
    });



    const handleUpdateProfile = () => {
        mutate({
            id: 2,
            data: {
                email: studentData.email,
                fullName : studentData.fullName,
                birthDate : studentData.birthDate,
                gender : studentData.gender
            }
        });
        setIsEditing(false);
    };

    const handleCancel = () => {
        setIsEditing(false);
    };

    return (
        <div className="space-y-6">
            {/* Stats Cards */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="bg-white rounded-lg shadow p-6">
                    <div className="flex items-center">
                        <div className="p-3 bg-blue-100 rounded-lg">
                            <BookOpen className="w-6 h-6 text-blue-600" />
                        </div>
                        <div className="ml-4">
                            <p className="text-sm text-gray-600">Khóa học đang tham gia</p>
                            <p className="text-2xl font-semibold text-gray-900">3</p>
                        </div>
                    </div>
                </div>

                <div className="bg-white rounded-lg shadow p-6">
                    <div className="flex items-center">
                        <div className="p-3 bg-green-100 rounded-lg">
                            <CheckCircle className="w-6 h-6 text-green-600" />
                        </div>
                        <div className="ml-4">
                            <p className="text-sm text-gray-600">Khóa học hoàn thành</p>
                            <p className="text-2xl font-semibold text-gray-900">8</p>
                        </div>
                    </div>
                </div>


            </div>

            {/* Thông tin cá nhân */}
            <div className="bg-white rounded-lg shadow">
                <div className="px-6 py-4 border-b border-gray-200">
                    <div className="flex items-center justify-between">
                        <h3 className="text-lg font-semibold text-gray-900">Thông tin cá nhân</h3>
                        <button
                            onClick={() => setIsEditing(!isEditing)}
                            className="flex items-center space-x-2 px-4 py-2 text-sm text-blue-600 hover:text-blue-800 transition-colors"
                        >
                            {isEditing ? (
                                <>
                                    <X className="w-4 h-4" />
                                    <span>Hủy</span>
                                </>
                            ) : (
                                <>
                                    <Edit className="w-4 h-4" />
                                    <span>Chỉnh sửa</span>
                                </>
                            )}
                        </button>
                    </div>
                </div>

                <div className="p-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    <Mail className="w-4 h-4 inline mr-2" />
                                    FullName
                                </label>
                                {isEditing ? (
                                    <input
                                        type="fullName"
                                        value={studentData?.fullName}
                                        onChange={(e) => setStudentData({ ...studentData, fullName: e.target.value })}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                ) : (
                                    <p className="text-gray-900">{studentData?.fullName}</p>
                                )}
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    <Mail className="w-4 h-4 inline mr-2" />
                                    Email
                                </label>
                                {isEditing ? (
                                    <input
                                        type="email"
                                        value={studentData?.email}
                                        onChange={(e) => setStudentData({ ...studentData, email: e.target.value })}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                ) : (
                                    <p className="text-gray-900">{studentData?.email}</p>
                                )}
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    <Calendar className="w-4 h-4 inline mr-2" />
                                    Ngày sinh
                                </label>
                                {isEditing ? (
                                    <input
                                        type="date"
                                        value={studentData?.birthDate}
                                        onChange={(e) => setStudentData({ ...studentData, birthDate: e.target.value })}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                ) : (
                                    <p className="text-gray-900">{studentData?.birthDate}</p>
                                )}
                            </div>
                        </div>

                        <div className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    <Mars className="w-4 h-4 inline mr-2" />
                                    Giới tính
                                </label>
                                {isEditing ? (
                                    <select
                                        value={studentData.gender}
                                        onChange={(e) => setStudentData({ ...studentData, gender: e.target.value })}
                                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    >
                                        <option value="">Chọn giới tính</option>
                                        <option value="Nam">Nam</option>
                                        <option value="Nữ">Nữ</option>
                                        <option value="Khác">Khác</option>
                                    </select>
                                ) : (
                                    <p className="text-gray-900">{studentData.gender}</p>
                                )}
                            </div>


                        </div>
                    </div>

                    {isEditing && (
                        <div className="mt-6 flex justify-end space-x-3">
                            <button
                                onClick={handleCancel}
                                className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
                            >
                                Hủy
                            </button>
                            <button
                                onClick={handleUpdateProfile}
                                className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
                            >
                                <Save className="w-4 h-4" />
                                <span>Lưu thay đổi</span>
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}