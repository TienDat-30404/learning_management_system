'use client'
import React, { useState } from 'react';
import {
  User,
  Mail,
  Phone,
  Calendar,
  MapPin,
  BookOpen,
  Trophy,
  Clock,
  Edit,
  Save,
  X,
  GraduationCap,
  Target,
  Award,
  TrendingUp,
  CheckCircle,
  PlayCircle,
  FileText,
  Settings
} from 'lucide-react';
import OverviewProfile from '@/components/profile/OverviewProfile';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import CourseProgress from '@/components/profile/CourseProgress';

const StudentProfile = () => {
  const [activeTab, setActiveTab] = useState('overview');
  const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user)

  const achievements = [
    {
      id: 1,
      title: 'Học viên xuất sắc',
      description: 'Hoàn thành 5 khóa học với điểm A',
      date: '2024-06-01',
      icon: Trophy,
      color: 'text-yellow-500'
    },
    {
      id: 2,
      title: 'Chuyên cần',
      description: 'Tham gia đầy đủ 100% buổi học',
      date: '2024-05-15',
      icon: Target,
      color: 'text-blue-500'
    },
    {
      id: 3,
      title: 'Người học tích cực',
      description: 'Tham gia thảo luận và đóng góp ý kiến',
      date: '2024-04-20',
      icon: Award,
      color: 'text-green-500'
    }
  ];


  const tabs = [
    { id: 'overview', label: 'Tổng quan', icon: User },
    { id: 'courses', label: 'Khóa học', icon: BookOpen },
    { id: 'achievements', label: 'Thành tích', icon: Trophy },
    { id: 'settings', label: 'Cài đặt', icon: Settings }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center space-x-4">
              <GraduationCap className="w-8 h-8 text-blue-600" />
              <h1 className="text-xl font-semibold text-gray-900">LMS - Hồ sơ học viên</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-500">Xin chào, {userInfo?.fullName}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          {/* Sidebar */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow p-6">
              {/* Avatar và thông tin cơ bản */}
              <div className="text-center mb-6">
                <div className="relative inline-block">
                  {/* <img
                    src={studentData.avatar}
                    alt={studentData.name}
                    className="w-24 h-24 rounded-full mx-auto  mb-4 object-cover"
                  /> */}
                </div>
                <h2 className="text-xl font-semibold text-gray-900">{userInfo?.fullName}</h2>
                {/* <p className="text-gray-500">jgrwejtjer</p> */}
                {/* <p className="text-sm text-gray-600">Công nghệ thông tin</p> */}
              </div>

              {/* Navigation */}
              <nav className="space-y-2">
                {tabs.map((tab) => {
                  const Icon = tab.icon;
                  return (
                    <button
                      key={tab.id}
                      onClick={() => setActiveTab(tab.id)}
                      className={`w-full flex items-center space-x-3 px-3 py-2 rounded-lg text-left transition-colors ${activeTab === tab.id
                        ? 'bg-blue-50 text-blue-700 border-l-4 border-blue-700'
                        : 'text-gray-700 hover:bg-gray-50'
                        }`}
                    >
                      <Icon className="w-5 h-5" />
                      <span>{tab.label}</span>
                    </button>
                  );
                })}
              </nav>
            </div>
          </div>

          {/* Main Content */}
          <div className="lg:col-span-3">

            {/* Overview */}
            {activeTab === 'overview' && (
              <OverviewProfile />
            )}

            {/* progress course */}
            {activeTab === 'courses' && (
              <CourseProgress />
            )}

            {/* Achievements Tab */}
            {activeTab === 'achievements' && (
              <div className="bg-white rounded-lg shadow">
                <div className="px-6 py-4 border-b border-gray-200">
                  <h3 className="text-lg font-semibold text-gray-900">Thành tích và giải thưởng</h3>
                </div>
                <div className="p-6">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {achievements.map((achievement) => {
                      const Icon = achievement.icon;
                      return (
                        <div key={achievement.id} className="border border-gray-200 rounded-lg p-4">
                          <div className="flex items-start space-x-3">
                            <div className={`p-2 rounded-lg ${achievement.color} bg-opacity-10`}>
                              <Icon className={`w-6 h-6 ${achievement.color}`} />
                            </div>
                            <div className="flex-1">
                              <h4 className="font-medium text-gray-900">{achievement.title}</h4>
                              <p className="text-sm text-gray-600 mt-1">{achievement.description}</p>
                              <p className="text-xs text-gray-500 mt-2">
                                {new Date(achievement.date).toLocaleDateString('vi-VN')}
                              </p>
                            </div>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
            )}

            {/* Settings Tab */}
            {activeTab === 'settings' && (
              <div className="bg-white rounded-lg shadow">
                <div className="px-6 py-4 border-b border-gray-200">
                  <h3 className="text-lg font-semibold text-gray-900">Cài đặt tài khoản</h3>
                </div>
                <div className="p-6">
                  <div className="space-y-6">
                    <div>
                      <h4 className="font-medium text-gray-900 mb-3">Thông báo</h4>
                      <div className="space-y-3">
                        <label className="flex items-center">
                          <input type="checkbox" className="rounded border-gray-300 text-blue-600 focus:ring-blue-500" defaultChecked />
                          <span className="ml-2 text-sm text-gray-700">Thông báo về bài tập mới</span>
                        </label>
                        <label className="flex items-center">
                          <input type="checkbox" className="rounded border-gray-300 text-blue-600 focus:ring-blue-500" defaultChecked />
                          <span className="ml-2 text-sm text-gray-700">Nhắc nhở deadline</span>
                        </label>
                        <label className="flex items-center">
                          <input type="checkbox" className="rounded border-gray-300 text-blue-600 focus:ring-blue-500" />
                          <span className="ml-2 text-sm text-gray-700">Thông báo qua email</span>
                        </label>
                      </div>
                    </div>

                    <div>
                      <h4 className="font-medium text-gray-900 mb-3">Bảo mật</h4>
                      <div className="space-y-3">
                        <button className="w-full text-left px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors">
                          Đổi mật khẩu
                        </button>
                        <button className="w-full text-left px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors">
                          Xác thực 2 yếu tố
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentProfile;