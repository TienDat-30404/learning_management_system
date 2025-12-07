import React, { useEffect, useState } from 'react';
import { X, Upload, Book, Tag, FileText, Image } from 'lucide-react';
import { Course, UpdateCourse } from '@/types/course';
import { useGetAllCategories } from '@/hooks/useCategories';
import { Category } from '@/types/category';
import { useUpdateCourse } from '@/hooks/useCourses';
import { QueryParams } from '@/types/common';
import { CreateLesson } from '@/types/lesson';
import { useCreateLesson } from '@/hooks/useLessons';


interface LessonCreateModalProps {
  isOpen: boolean;
  onClose: () => void;
  course: UpdateCourse | null;
}

export const LessonCreateModal: React.FC<LessonCreateModalProps> = ({
  isOpen,
  onClose,
  course
}) => {

  const [formData, setFormData] = useState<CreateLesson>({
    title: '',
    content: '',
    courseId: 0,
    videoUrl: ''
  });


  const [file, setFile] = useState<File | null>(null);

  const handleVideoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setFile(file)
    }
  };

  const handleCloseModal = () => {
    onClose();
    setFile(null);
  }

  const { mutate: confirmCreateLesson, isError, isPending, isSuccess, data } = useCreateLesson();
  console.log("course", course)
  const handleSubmit = () => {
    const formDataTosend = new FormData()
    formDataTosend.append('title', formData?.title)
    formDataTosend.append('content', formData?.content)
    formDataTosend.append('courseId', String(course?.id))
    if (file) {
      formDataTosend.append('videoUrl', file);
    }
    confirmCreateLesson({
      data: formDataTosend
    });

    // handleCloseModal()
  };


  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };


  return (
    <div className={`fixed inset-0 ${isOpen ? 'flex' : 'hidden'} bg-transparent bg-opacity-40 flex items-center justify-center z-50 p-4`}>
      <div className="bg-white rounded-2xl shadow-xl w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h2 className="text-xl font-bold text-gray-800 flex items-center">
            <Book className="mr-3 text-blue-600" size={24} />
            Tạo bài học cho khóa học : {course?.title}
          </h2>
          <button
            onClick={() => handleCloseModal()}
            className="p-2 hover:bg-gray-100 rounded-full transition-colors"
          >
            <X size={20} className="text-gray-600" />
          </button>
        </div>

        {/* Form */}
        <div className="p-6 space-y-6">
          {/* Tên khóa học */}
          <div>
            <label htmlFor="name" className="block text-sm font-semibold text-gray-700 mb-2">
              <Book size={16} className="inline mr-2" />
              Tên khóa học
            </label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData?.title}
              onChange={handleInputChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              placeholder="Nhập tên khóa học..."
              required
            />
          </div>


          <div>
            <label htmlFor="name" className="block text-sm font-semibold text-gray-700 mb-2">
              <Book size={16} className="inline mr-2" />
              Nội dung khóa học
            </label>
            <input
              type="text"
              id="content"
              name="content"
              value={formData?.content}
              onChange={handleInputChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              placeholder="Nhập tên khóa học..."
              required
            />
          </div>

          {/* Ảnh khóa học */}
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">
              <Image size={16} className="inline mr-2" />
              Video khóa học
            </label>

            {/* Upload Button */}
            <div className="relative">
              <input
                type="file"
                id="video"
                accept="video/*"
                onChange={handleVideoChange}
                className="hidden"
              />
              <label
                htmlFor="video"
                className="flex items-center justify-center w-full px-4 py-3 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer hover:border-blue-400 hover:bg-blue-50 transition-all"
              >
                <Upload size={20} className="mr-2 text-gray-600" />
                <span className="text-gray-600">
                  {file ? `Đã chọn: ${file.name}` : 'Chọn video bài giảng'}
                </span>
              </label>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex justify-end space-x-3 pt-4 border-t border-gray-200">

            <button
              type="button"
              onClick={() => handleCloseModal()}
              className="px-6 py-3 text-gray-700 bg-white border border-gray-300 hover:bg-gray-50 rounded-lg font-medium transition-colors"
            >
              Hủy
            </button>
            <button
              type="button"
              onClick={() => handleSubmit()}
              className="px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-lg font-medium transition-colors"
            >
              {isPending ? 'Đang lưu...' : 'Lưu thay đổi'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};