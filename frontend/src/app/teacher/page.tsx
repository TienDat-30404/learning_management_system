'use client'

import React, { useState } from 'react';
import {
  BookOpen,
  Users,
  Calendar,
  FileText,
  BarChart3,
  Settings,
  Bell,
  Search,
  Plus,
  User,
  ChevronRight,
  Clock,
  CheckCircle,
  AlertCircle,
  TrendingUp,
  Award,
  MessageCircle,
  Upload,
  Edit,
  Trash2,
  Eye,
  Book,
  BookType
} from 'lucide-react';
import { useDashboardTeacher } from '@/hooks/useTeacher';
import { DashboardTeacher } from '@/types/teacher';
import DashboardContent from '@/components/teacher/DashboardContent';
import StudentContent from '@/components/teacher/StudentContent';

interface Course {
  id: string;
  name: string;
  students: number;
  lesson: number;
  status: 'active' | 'draft' | 'completed';
}

interface Assignment {
  id: string;
  title: string;
  course: string;
  dueDate: string;
  submissions: number;
  totalStudents: number;
  status: 'pending' | 'grading' | 'completed';
}

interface Student {
  id: string;
  name: string;
  email: string;
  course: string;
  progress: number;
  lastActive: string;
}

const TeacherLMSDashboard: React.FC = () => {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [selectedCourse, setSelectedCourse] = useState<string>('');

  // Mock data
  const courses: Course[] = [
    { id: '1', name: 'Lập trình Web Frontend', students: 45, lesson: 78, status: 'active' },
    { id: '2', name: 'JavaScript Nâng cao', students: 32, lesson: 65, status: 'active' },
    { id: '3', name: 'React & TypeScript', students: 28, lesson: 42, status: 'active' },
    { id: '4', name: 'Thiết kế UI/UX', students: 38, lesson: 90, status: 'completed' },
  ];

  const assignments: Assignment[] = [
    { id: '1', title: 'Bài tập React Components', course: 'React & TypeScript', dueDate: '2025-09-25', submissions: 18, totalStudents: 28, status: 'pending' },
    { id: '2', title: 'Project Website cá nhân', course: 'Lập trình Web Frontend', dueDate: '2025-09-28', submissions: 35, totalStudents: 45, status: 'grading' },
    { id: '3', title: 'Thiết kế Prototype App', course: 'Thiết kế UI/UX', dueDate: '2025-09-22', submissions: 38, totalStudents: 38, status: 'completed' },
  ];

  const students: Student[] = [
    { id: '1', name: 'Nguyễn Văn An', email: 'an@email.com', course: 'React & TypeScript', progress: 85, lastActive: '2 giờ trước' },
    { id: '2', name: 'Trần Thị Bình', email: 'binh@email.com', course: 'Lập trình Web Frontend', progress: 92, lastActive: '1 ngày trước' },
    { id: '3', name: 'Lê Hoàng Cường', email: 'cuong@email.com', course: 'JavaScript Nâng cao', progress: 67, lastActive: '3 giờ trước' },
  ];

  const menuItems = [
    { id: 'dashboard', label: 'Trang chủ', icon: BarChart3 },
    { id: 'students', label: 'Học viên', icon: Users },
    { id: 'assignments', label: 'Bài tập', icon: FileText },
    { id: 'schedule', label: 'Lịch dạy', icon: Calendar },
    { id: 'messages', label: 'Tin nhắn', icon: MessageCircle },
    { id: 'settings', label: 'Cài đặt', icon: Settings },
  ];

 



  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard':
        return <DashboardContent />
      case 'students':
        return <StudentContent />
      case 'assignments':
        return (
          <div className="bg-white p-6 rounded-lg shadow-sm border">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">Quản lý Bài tập</h2>
            <p className="text-gray-600">Tính năng quản lý bài tập đang được phát triển...</p>
          </div>
        );
      case 'schedule':
        return (
          <div className="bg-white p-6 rounded-lg shadow-sm border">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">Lịch dạy</h2>
            <p className="text-gray-600">Tính năng lịch dạy đang được phát triển...</p>
          </div>
        );
      case 'messages':
        return (
          <div className="bg-white p-6 rounded-lg shadow-sm border">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">Tin nhắn</h2>
            <p className="text-gray-600">Tính năng tin nhắn đang được phát triển...</p>
          </div>
        );
      case 'settings':
        return (
          <div className="bg-white p-6 rounded-lg shadow-sm border">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">Cài đặt</h2>
            <p className="text-gray-600">Tính năng cài đặt đang được phát triển...</p>
          </div>
        );
      default:
        return DashboardContent();
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="flex items-center justify-between px-6 py-4">
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-2">
              <BookOpen className="w-8 h-8 text-blue-600" />
              <h1 className="text-xl font-bold text-gray-900">EduLMS</h1>
            </div>
          </div>

          <div className="flex items-center space-x-4">
            <div className="relative">
              <Search className="w-5 h-5 absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Tìm kiếm..."
                className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-64"
              />
            </div>

            <button className="relative p-2 text-gray-400 hover:text-gray-500">
              <Bell className="w-5 h-5" />
              <span className="absolute -top-1 -right-1 h-4 w-4 bg-red-500 rounded-full text-xs text-white flex items-center justify-center">
                3
              </span>
            </button>

            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-gray-300 rounded-full flex items-center justify-center">
                <User className="w-5 h-5 text-gray-600" />
              </div>
              <span className="text-sm font-medium text-gray-700">Cô Nguyễn Mai</span>
            </div>
          </div>
        </div>
      </header>

      <div className="flex">
        {/* Sidebar */}
        <aside className="w-64 bg-white shadow-sm border-r min-h-screen">
          <nav className="mt-6">
            <div className="px-3">
              {menuItems.map((item) => {
                const IconComponent = item.icon;
                return (
                  <button
                    key={item.id}
                    onClick={() => setActiveTab(item.id)}
                    className={`w-full flex items-center px-3 py-2 text-sm font-medium rounded-lg mb-1 transition-colors ${activeTab === item.id
                      ? 'bg-blue-100 text-blue-700'
                      : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
                      }`}
                  >
                    <IconComponent className="w-5 h-5 mr-3" />
                    {item.label}
                  </button>
                );
              })}
            </div>
          </nav>
        </aside>

        {/* Main Content */}
        <main className="flex-1 p-6">
          {renderContent()}
        </main>
      </div>
    </div>
  );
};

export default TeacherLMSDashboard;