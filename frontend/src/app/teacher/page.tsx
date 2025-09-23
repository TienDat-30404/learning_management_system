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

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'active': return 'bg-green-100 text-green-800';
      case 'draft': return 'bg-gray-100 text-gray-800';
      case 'completed': return 'bg-blue-100 text-blue-800';
      case 'pending': return 'bg-yellow-100 text-yellow-800';
      case 'grading': return 'bg-orange-100 text-orange-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(0)
  const { data: datasDashboard } = useDashboardTeacher({
    page: Number(page),
    size: Number(size)
  })

  console.log("rn23r23r23", datasDashboard)
  const totalNumerStudent = datasDashboard?.data?.content?.reduce((sum : number, data : DashboardTeacher) => sum + data.quantityStudent, 0)
  console.log("ewrer", totalNumerStudent)

  const renderDashboard = () => (
    <div className="space-y-6">
      {/* Stats Overview */}
      <div className="grid  lg:grid-cols-5 gap-6 ">
        <div className="bg-white p-6 rounded-lg shadow-sm border">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Tổng học viên</p>
              <p className="text-3xl font-bold text-gray-900">{totalNumerStudent}</p>
            </div>
            <div className="bg-blue-100 p-3 rounded-lg">
              <Users className="w-6 h-6 text-blue-600" />
            </div>
          </div>
          {/* <div className="flex items-center mt-4 text-sm">
            <TrendingUp className="w-4 h-4 text-green-500 mr-1" />
            <span className="text-green-500">+12%</span>
            <span className="text-gray-500 ml-1">so với tháng trước</span>
          </div> */}
        </div>

        <div className="bg-white p-6 rounded-lg shadow-sm border">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Tổng số khóa học</p>
              <p className="text-3xl font-bold text-gray-900">{datasDashboard?.data?.content?.length}</p>
            </div>
            <div className="bg-green-100 p-3 rounded-lg">
              <BookOpen className="w-6 h-6 text-green-600" />
            </div>
          </div>
          {/* <div className="flex items-center mt-4 text-sm">
            <CheckCircle className="w-4 h-4 text-green-500 mr-1" />
            <span className="text-gray-500">3 khóa đang hoạt động</span>
          </div> */}
        </div>

        <div className="bg-white p-6 rounded-lg shadow-sm border">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Quiz/Trắc nghiệm</p>
              <p className="text-3xl font-bold text-gray-900">{datasDashboard?.data?.totalQuizs}</p>
            </div>
            <div className="bg-green-100 p-3 rounded-lg">
              <CheckCircle className="w-6 h-6 text-green-600" />
            </div>
          </div>
          <div className="flex items-center mt-4 text-sm">
            <span className="text-green-500">Tự động chấm điểm</span>
          </div>
        </div>

        
      </div>

      {/* Courses Management Section */}
      <div className="bg-white rounded-lg shadow-sm border">
        <div className="p-6 border-b">
          <div className="flex justify-between items-center">
            <h3 className="text-lg font-semibold">Danh sách khóa học</h3>
            {/* <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center space-x-2">
              <Plus className="w-4 h-4" />
              <span>Tạo khóa học mới</span>
            </button> */}
          </div>
        </div>

        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Tên khóa học
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Số học viên
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Số bài giảng
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thể loại
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thao tác
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {datasDashboard?.data?.content?.map((course : DashboardTeacher) => (
                <tr key={course.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="flex-shrink-0 h-10 w-10">
                        <div className="h-10 w-10 rounded-lg bg-gradient-to-r from-blue-500 to-purple-600 flex items-center justify-center">
                          <BookOpen className="w-5 h-5 text-white" />
                        </div>
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{course?.title}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <Users className="w-4 h-4 text-gray-400 mr-2" />
                      <span className="text-sm text-gray-900">{course.quantityStudent} học viên</span>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <Book className="w-4 h-4 text-gray-400 mr-2" />
                      <span className="text-sm text-gray-900">{course.totalLesson} bài giảng</span>
                    </div>
                  </td>

                   <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <BookType className="w-4 h-4 text-gray-400 mr-2" />
                      <span className="text-sm text-gray-900">{course?.category?.name}</span>
                    </div>
                  </td>
                 
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <div className="flex justify-end space-x-2">
                      <button className="bg-gray-100 text-gray-700 px-3 py-1.5 rounded hover:bg-gray-200 flex items-center space-x-1">
                        <Eye className="w-4 h-4" />
                        <span>Xem</span>
                      </button>
                      <button className="bg-blue-100 text-blue-700 px-3 py-1.5 rounded hover:bg-blue-200 flex items-center space-x-1">
                        <Edit className="w-4 h-4" />
                        <span>Sửa</span>
                      </button>
                      <button className="bg-red-100 text-red-700 px-3 py-1.5 rounded hover:bg-red-200 flex items-center space-x-1">
                        <Trash2 className="w-4 h-4" />
                        <span>Xóa</span>
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );

  const renderStudents = () => (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-900">Quản lý Học viên</h2>
        <div className="flex space-x-2">
          <select className="border border-gray-300 rounded-lg px-3 py-2 text-sm">
            <option value="">Tất cả khóa học</option>
            {courses.map(course => (
              <option key={course.id} value={course.id}>{course.name}</option>
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
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Hoạt động cuối
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thao tác
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {students.map((student) => (
                <tr key={student.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="flex-shrink-0 h-10 w-10">
                        <div className="h-10 w-10 rounded-full bg-gray-300 flex items-center justify-center">
                          <User className="w-5 h-5 text-gray-600" />
                        </div>
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{student.name}</div>
                        <div className="text-sm text-gray-500">{student.email}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {student.course}
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
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {student.lastActive}
                  </td>
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
  );

  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard':
        return renderDashboard();
      case 'students':
        return renderStudents();
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
        return renderDashboard();
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