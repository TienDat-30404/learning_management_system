'use client'

import React, { useState } from 'react'
import { 
  Users, BookOpen, Award, BarChart3, Calendar, Bell, Search, Plus,
  ChevronDown, Settings, LogOut, MessageSquare, Clock, TrendingUp,
  FileText, Video, CheckCircle, AlertCircle, User, Eye, Edit, Trash2
} from 'lucide-react'

interface Course {
  id: string
  title: string
  students: number
  progress: number
  status: 'active' | 'draft' | 'completed'
  lastUpdated: string
}

interface Student {
  id: string
  name: string
  avatar: string
  course: string
  progress: number
  lastActive: string
  status: 'online' | 'offline'
}

interface Assignment {
  id: string
  title: string
  course: string
  dueDate: string
  submitted: number
  total: number
  status: 'pending' | 'graded'
}

const TeacherDashboard: React.FC = () => {
  const [activeTab, setActiveTab] = useState('overview')
  const [showNotifications, setShowNotifications] = useState(false)
  
  // Mock data
  const courses: Course[] = [
    { id: '1', title: 'Lập trình React', students: 45, progress: 78, status: 'active', lastUpdated: '2 giờ trước' },
    { id: '2', title: 'JavaScript Nâng cao', students: 32, progress: 65, status: 'active', lastUpdated: '1 ngày trước' },
    { id: '3', title: 'Node.js Backend', students: 28, progress: 42, status: 'draft', lastUpdated: '3 ngày trước' },
    { id: '4', title: 'Database Design', students: 38, progress: 90, status: 'completed', lastUpdated: '1 tuần trước' }
  ]

  const recentStudents: Student[] = [
    { id: '1', name: 'Nguyễn Văn An', avatar: 'NA', course: 'React', progress: 85, lastActive: '5 phút trước', status: 'online' },
    { id: '2', name: 'Trần Thị Bình', avatar: 'TB', course: 'JavaScript', progress: 72, lastActive: '1 giờ trước', status: 'offline' },
    { id: '3', name: 'Lê Minh Cường', avatar: 'LC', course: 'Node.js', progress: 90, lastActive: '30 phút trước', status: 'online' },
    { id: '4', name: 'Phạm Thu Hà', avatar: 'PH', course: 'Database', progress: 67, lastActive: '2 giờ trước', status: 'offline' }
  ]

  const assignments: Assignment[] = [
    { id: '1', title: 'Bài tập React Hooks', course: 'React', dueDate: '2024-06-25', submitted: 32, total: 45, status: 'pending' },
    { id: '2', title: 'Project Node.js API', course: 'Node.js', dueDate: '2024-06-22', submitted: 28, total: 28, status: 'graded' },
    { id: '3', title: 'Database Schema Design', course: 'Database', dueDate: '2024-06-30', submitted: 15, total: 38, status: 'pending' }
  ]

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'active': return 'bg-green-100 text-green-800'
      case 'draft': return 'bg-yellow-100 text-yellow-800'
      case 'completed': return 'bg-blue-100 text-blue-800'
      case 'online': return 'bg-green-500'
      case 'offline': return 'bg-gray-400'
      case 'pending': return 'bg-orange-100 text-orange-800'
      case 'graded': return 'bg-green-100 text-green-800'
      default: return 'bg-gray-100 text-gray-800'
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            {/* Logo & Title */}
            <div className="flex items-center space-x-4">
              <div className="flex items-center space-x-3">
                <div className="w-10 h-10 bg-gradient-to-br from-blue-600 to-purple-600 rounded-xl flex items-center justify-center">
                  <BookOpen className="w-6 h-6 text-white" />
                </div>
                <div>
                  <h1 className="text-xl font-bold text-gray-900">EduLMS Pro</h1>
                  <p className="text-sm text-gray-500">Dashboard Giáo viên</p>
                </div>
              </div>
            </div>

            {/* Search & Actions */}
            <div className="flex items-center space-x-4">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
                <input
                  type="text"
                  placeholder="Tìm kiếm..."
                  className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              </div>
              
              <div className="relative">
                <button
                  onClick={() => setShowNotifications(!showNotifications)}
                  className="p-2 text-gray-400 hover:text-gray-600 relative"
                >
                  <Bell className="w-6 h-6" />
                  <span className="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full"></span>
                </button>
              </div>

              <div className="flex items-center space-x-3">
                <div className="w-8 h-8 bg-gradient-to-br from-blue-600 to-purple-600 rounded-full flex items-center justify-center">
                  <span className="text-white text-sm font-medium">GV</span>
                </div>
                <div className="hidden md:block">
                  <p className="text-sm font-medium text-gray-900">Thầy Nguyễn Văn A</p>
                  <p className="text-xs text-gray-500">Giáo viên</p>
                </div>
                <ChevronDown className="w-4 h-4 text-gray-400" />
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">Tổng Khóa học</p>
                <p className="text-3xl font-bold text-gray-900">12</p>
                <p className="text-sm text-green-600 mt-1">
                  <TrendingUp className="inline w-4 h-4 mr-1" />
                  +2 tháng này
                </p>
              </div>
              <div className="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center">
                <BookOpen className="w-6 h-6 text-blue-600" />
              </div>
            </div>
          </div>

          <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">Tổng Học viên</p>
                <p className="text-3xl font-bold text-gray-900">143</p>
                <p className="text-sm text-green-600 mt-1">
                  <TrendingUp className="inline w-4 h-4 mr-1" />
                  +18 tháng này
                </p>
              </div>
              <div className="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center">
                <Users className="w-6 h-6 text-green-600" />
              </div>
            </div>
          </div>

          <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">Bài tập chờ</p>
                <p className="text-3xl font-bold text-gray-900">47</p>
                <p className="text-sm text-orange-600 mt-1">
                  <Clock className="inline w-4 h-4 mr-1" />
                  Cần chấm điểm
                </p>
              </div>
              <div className="w-12 h-12 bg-orange-100 rounded-xl flex items-center justify-center">
                <FileText className="w-6 h-6 text-orange-600" />
              </div>
            </div>
          </div>

          <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">Hoàn thành TB</p>
                <p className="text-3xl font-bold text-gray-900">78%</p>
                <p className="text-sm text-green-600 mt-1">
                  <TrendingUp className="inline w-4 h-4 mr-1" />
                  +5% tuần này
                </p>
              </div>
              <div className="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center">
                <Award className="w-6 h-6 text-purple-600" />
              </div>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Main Content */}
          <div className="lg:col-span-2 space-y-8">
            {/* My Courses */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <div className="flex items-center justify-between">
                  <h2 className="text-lg font-semibold text-gray-900">Khóa học của tôi</h2>
                  <button className="flex items-center space-x-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                    <Plus className="w-4 h-4" />
                    <span>Thêm khóa học</span>
                  </button>
                </div>
              </div>
              
              <div className="p-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {courses.map((course) => (
                    <div key={course.id} className="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow">
                      <div className="flex items-start justify-between mb-3">
                        <h3 className="font-medium text-gray-900 flex-1">{course.title}</h3>
                        <span className={`px-2 py-1 text-xs rounded-full ${getStatusColor(course.status)}`}>
                          {course.status === 'active' ? 'Đang diễn ra' : 
                           course.status === 'draft' ? 'Nháp' : 'Hoàn thành'}
                        </span>
                      </div>
                      
                      <div className="flex items-center space-x-4 text-sm text-gray-600 mb-3">
                        <div className="flex items-center space-x-1">
                          <Users className="w-4 h-4" />
                          <span>{course.students} học viên</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <Clock className="w-4 h-4" />
                          <span>{course.lastUpdated}</span>
                        </div>
                      </div>

                      <div className="mb-3">
                        <div className="flex items-center justify-between text-sm mb-1">
                          <span className="text-gray-600">Tiến độ</span>
                          <span className="font-medium">{course.progress}%</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-blue-600 h-2 rounded-full transition-all duration-300"
                            style={{ width: `${course.progress}%` }}
                          ></div>
                        </div>
                      </div>

                      <div className="flex items-center space-x-2">
                        <button className="flex-1 px-3 py-2 text-sm bg-blue-50 text-blue-600 rounded-lg hover:bg-blue-100 transition-colors">
                          <Eye className="w-4 h-4 inline mr-1" />
                          Xem
                        </button>
                        <button className="flex-1 px-3 py-2 text-sm bg-gray-50 text-gray-600 rounded-lg hover:bg-gray-100 transition-colors">
                          <Edit className="w-4 h-4 inline mr-1" />
                          Chỉnh sửa
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Recent Assignments */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900">Bài tập gần đây</h2>
              </div>
              
              <div className="p-6">
                <div className="space-y-4">
                  {assignments.map((assignment) => (
                    <div key={assignment.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                      <div className="flex-1">
                        <h3 className="font-medium text-gray-900">{assignment.title}</h3>
                        <p className="text-sm text-gray-600 mt-1">{assignment.course}</p>
                        <p className="text-xs text-gray-500 mt-1">Hạn nộp: {assignment.dueDate}</p>
                      </div>
                      
                      <div className="text-center">
                        <p className="text-sm font-medium text-gray-900">
                          {assignment.submitted}/{assignment.total}
                        </p>
                        <p className="text-xs text-gray-500">Đã nộp</p>
                      </div>
                      
                      <div className="flex items-center space-x-2">
                        <span className={`px-2 py-1 text-xs rounded-full ${getStatusColor(assignment.status)}`}>
                          {assignment.status === 'pending' ? 'Chờ chấm' : 'Đã chấm'}
                        </span>
                        <button className="p-2 text-gray-400 hover:text-gray-600">
                          <Eye className="w-4 h-4" />
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            {/* Quick Actions */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900">Thao tác nhanh</h2>
              </div>
              
              <div className="p-6">
                <div className="space-y-3">
                  <button className="w-full flex items-center space-x-3 p-3 text-left bg-blue-50 hover:bg-blue-100 rounded-lg transition-colors">
                    <Plus className="w-5 h-5 text-blue-600" />
                    <span className="text-blue-600 font-medium">Tạo bài giảng mới</span>
                  </button>
                  
                  <button className="w-full flex items-center space-x-3 p-3 text-left bg-green-50 hover:bg-green-100 rounded-lg transition-colors">
                    <FileText className="w-5 h-5 text-green-600" />
                    <span className="text-green-600 font-medium">Tạo bài tập</span>
                  </button>
                  
                  <button className="w-full flex items-center space-x-3 p-3 text-left bg-purple-50 hover:bg-purple-100 rounded-lg transition-colors">
                    <Video className="w-5 h-5 text-purple-600" />
                    <span className="text-purple-600 font-medium">Bắt đầu live stream</span>
                  </button>
                  
                  <button className="w-full flex items-center space-x-3 p-3 text-left bg-orange-50 hover:bg-orange-100 rounded-lg transition-colors">
                    <BarChart3 className="w-5 h-5 text-orange-600" />
                    <span className="text-orange-600 font-medium">Xem báo cáo</span>
                  </button>
                </div>
              </div>
            </div>

            {/* Recent Students */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900">Học viên gần đây</h2>
              </div>
              
              <div className="p-6">
                <div className="space-y-4">
                  {recentStudents.map((student) => (
                    <div key={student.id} className="flex items-center space-x-3">
                      <div className="relative">
                        <div className="w-10 h-10 bg-gradient-to-br from-blue-500 to-purple-500 rounded-full flex items-center justify-center">
                          <span className="text-white text-sm font-medium">{student.avatar}</span>
                        </div>
                        <div className={`absolute -bottom-1 -right-1 w-3 h-3 ${getStatusColor(student.status)} rounded-full border-2 border-white`}></div>
                      </div>
                      
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-medium text-gray-900 truncate">{student.name}</p>
                        <p className="text-xs text-gray-500">{student.course} • {student.progress}% hoàn thành</p>
                        <p className="text-xs text-gray-400">{student.lastActive}</p>
                      </div>
                    </div>
                  ))}
                </div>
                
                <button className="w-full mt-4 px-4 py-2 text-sm text-blue-600 hover:bg-blue-50 rounded-lg transition-colors">
                  Xem tất cả học viên
                </button>
              </div>
            </div>

            {/* Upcoming Schedule */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <h2 className="text-lg font-semibold text-gray-900">Lịch sắp tới</h2>
              </div>
              
              <div className="p-6">
                <div className="space-y-3">
                  <div className="flex items-center space-x-3 p-3 bg-blue-50 rounded-lg">
                    <Calendar className="w-5 h-5 text-blue-600" />
                    <div>
                      <p className="text-sm font-medium text-blue-900">Lớp React Hooks</p>
                      <p className="text-xs text-blue-600">Hôm nay, 14:00</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center space-x-3 p-3 bg-green-50 rounded-lg">
                    <Video className="w-5 h-5 text-green-600" />
                    <div>
                      <p className="text-sm font-medium text-green-900">Live coding session</p>
                      <p className="text-xs text-green-600">Mai, 16:00</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center space-x-3 p-3 bg-orange-50 rounded-lg">
                    <CheckCircle className="w-5 h-5 text-orange-600" />
                    <div>
                      <p className="text-sm font-medium text-orange-900">Hạn nộp bài tập</p>
                      <p className="text-xs text-orange-600">25/06, 23:59</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default TeacherDashboard