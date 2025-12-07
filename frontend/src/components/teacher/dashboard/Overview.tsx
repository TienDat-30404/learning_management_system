import { useDashboardTeacher, useListStudentsOfTeacher } from "@/hooks/useTeacher";
import { DashboardTeacher } from "@/types/teacher";
import { Book, BookOpen, BookType, CheckCircle, CirclePlus, Edit, Eye, Trash2, Users } from "lucide-react";
import { useState } from "react";
import { CourseEditModal } from "./CourseEditModal";
import { UpdateCourse } from "@/types/course";
import { SourceTextModule } from "vm";
import { LessonCreateModal } from "./LessonCreateModal";


export default function Overview() {

    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(4)
    const params = { page: Number(page), size: Number(size) };

    const { data: datasDashboard } = useDashboardTeacher({
        page: Number(page),
        size: Number(size)
    })


    const totalNumerStudent = datasDashboard?.data?.content?.reduce((sum: number, data: DashboardTeacher) => sum + data.quantityStudent, 0)


    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isModalCreateLesson, setIsModalCreateLesson] = useState(false)
    const [selectedCourse, setSelectedCourse] = useState<UpdateCourse | null>(null);
    

    const handleEditCourse = (course: UpdateCourse) => {
        setSelectedCourse(course);
        setIsModalOpen(true);
    };

     const handleOpenModalCreateLesson = (course: UpdateCourse) => {
        setSelectedCourse(course);
        setIsModalCreateLesson(true);
    };



    return (
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

                <CourseEditModal
                    isOpen={isModalOpen}
                    course={selectedCourse}
                    onClose={() => {
                        setIsModalOpen(false);
                        setSelectedCourse(null);
                    }}
                    params={params}
                />

                <LessonCreateModal
                    isOpen = {isModalCreateLesson}
                    course = {selectedCourse}
                    onClose={() => {
                        setIsModalCreateLesson(false);
                        setSelectedCourse(null);
                    }}
                />

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
                            {datasDashboard?.data?.content?.map((course: DashboardTeacher) => (
                                <tr key={course.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 whitespace-nowrap">
                                        <div className="flex items-center">
                                            <div className="flex-shrink-0 h-10 w-10">
                                                <div className="h-10 w-10 rounded-lg bg-gradient-to-r from-blue-500 to-purple-600 flex items-center justify-center">
                                                    <img src={course?.image?.toString()} alt="" />
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
                                                <CirclePlus className="w-4 h-4" />
                                                <span
                                                    onClick={() => {
                                                        const courseToUpdate = {
                                                            id: course?.id,
                                                            title: String(course?.title),
                                                            image: String(course?.image),
                                                            description: course?.description, 
                                                            idCategory: course?.category?.id, 
                                                        };
                                                        handleOpenModalCreateLesson(courseToUpdate);
                                                    }}
                                                >
                                                    Tạo bài học</span>
                                            </button>
                                            <button className="bg-blue-100 text-blue-700 px-3 py-1.5 rounded hover:bg-blue-200 flex items-center space-x-1">
                                                <Edit className="w-4 h-4" />
                                                <span
                                                    onClick={() => {
                                                        const courseToUpdate = {
                                                            id: course?.id,
                                                            title: String(course?.title),
                                                            image: String(course?.image),
                                                            description: course?.description, 
                                                            idCategory: course?.category?.id, 
                                                        };
                                                        handleEditCourse(courseToUpdate);
                                                    }}
                                                >Sửa</span>
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
    )
}