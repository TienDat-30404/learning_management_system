import React, { useEffect, useState } from 'react';
import { X, Upload, Book, Tag, FileText, Image } from 'lucide-react';
import { Course, UpdateCourse } from '@/types/course';
import { useGetAllCategories } from '@/hooks/useCategories';
import { Category } from '@/types/category';
import { useUpdateCourse } from '@/hooks/useCourses';
import { QueryParams } from '@/types/common';


interface CourseEditModalProps {
  isOpen: boolean;
  course: UpdateCourse | null;
  onClose: () => void;
  params : QueryParams
}

export const CourseEditModal: React.FC<CourseEditModalProps> = ({
  isOpen,
  course,
  onClose,
  params
}) => {

  const [formData, setFormData] = useState<UpdateCourse>({
    id: 0,
    title: '',
    description: '',
    idCategory: 0,
    image: ''
  });

  useEffect(() => {
    if (course) {
      setFormData({
        id: course?.id ?? 0,
        title: course?.title ?? '',
        description: course?.description ?? '',
        idCategory: course?.idCategory ?? 0,
        image: course?.image ?? ''
      })
    }
  }, [course])

  const [file, setFile] = useState<File | null>(null);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setFile(file)
      const reader = new FileReader();
      reader.onload = (e) => {
        const imageUrl = e.target?.result as string;
        setFormData(prev => ({
          ...prev,
          image: imageUrl
        }));
      };
      reader.readAsDataURL(file);
    }
  };

  const { mutate: confirmUpdateCourse, isError, isPending, isSuccess, data } = useUpdateCourse(params);

  const handleSubmit = () => {
    const formDataTosend = new FormData()
    formDataTosend.append('title', formData?.title)
    formDataTosend.append('description', formData?.description)
    formDataTosend.append('categoryId', String(formData?.idCategory))
    if (file) {
      formDataTosend.append('image', file);
    }
    console.log("isPending", isPending)
    confirmUpdateCourse({
      id: formData?.id,
      data: formDataTosend
    });
    
    // onClose()
  };

  const { data: categories } = useGetAllCategories({
    page: 0,
    size: 100
  });

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
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            <Book className="mr-3 text-blue-600" size={24} />
            Chỉnh sửa khóa học
          </h2>
          <button
            onClick={onClose}
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

          {/* Mô tả khóa học */}
          <div>
            <label htmlFor="description" className="block text-sm font-semibold text-gray-700 mb-2">
              <FileText size={16} className="inline mr-2" />
              Mô tả khóa học
            </label>
            <textarea
              id="description"
              name="description"
              value={formData?.description}
              onChange={handleInputChange}
              rows={4}
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all resize-none"
              placeholder="Nhập mô tả chi tiết về khóa học..."
              required
            />
          </div>

          {/* Thể loại khóa học */}
          <div>
            <label htmlFor="category" className="block text-sm font-semibold text-gray-700 mb-2">
              <Tag size={16} className="inline mr-2" />
              Thể loại khóa học
            </label>
            <select
              id="category"
              name="idCategory"
              value={formData?.idCategory}
              onChange={handleInputChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              required
            >
              <option value="">Chọn thể loại...</option>
              {categories?.data?.content?.map((category: Category) => (
                <option key={category?.id} value={category?.id}>
                  {category?.name}
                </option>
              ))}
            </select>
          </div>

          {/* Ảnh khóa học */}
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">
              <Image size={16} className="inline mr-2" />
              Ảnh khóa học
            </label>

            {/* Image Preview */}
            {formData?.image && (
              <div className="mb-4">
                <img
                  src={formData?.image}
                  alt="Preview"
                  className="w-full h-48 object-cover rounded-lg border border-gray-200"
                />
              </div>
            )}

            {/* Upload Button */}
            <div className="relative">
              <input
                type="file"
                id="image"
                accept="image/*"
                onChange={handleImageChange}
                className="hidden"
              />
              <label
                htmlFor="image"
                className="flex items-center justify-center w-full px-4 py-3 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer hover:border-blue-400 hover:bg-blue-50 transition-all"
              >
                <Upload size={20} className="mr-2 text-gray-600" />
                <span className="text-gray-600">
                  {formData?.image ? 'Thay đổi ảnh' : 'Chọn ảnh khóa học'}
                </span>
              </label>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex justify-end space-x-3 pt-4 border-t border-gray-200">
            
            <button
              type="button"
              onClick={onClose}
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