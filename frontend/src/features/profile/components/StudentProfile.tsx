'use client'
import React, { useState } from 'react';
import {
  User,
  
  BookOpen,

  GraduationCap,

} from 'lucide-react';
import OverviewProfile from '@/features/profile/components/OverviewProfile';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import CourseProgress from './CourseProgress';

const StudentProfile = () => {
  const [activeTab, setActiveTab] = useState('overview');
  const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user)



  const tabs = [
    { id: 'overview', label: 'Tổng quan', icon: User },
    { id: 'courses', label: 'Khóa học', icon: BookOpen },
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

            

            
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentProfile;