'use-client'
import { BookOpen } from "lucide-react"
export default function Footer() {
    return (
        <footer className="bg-gray-900 text-white py-12">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
                    {/* Company Info */}
                    <div>
                        <div className="flex items-center mb-4">
                            <BookOpen className="w-8 h-8 text-blue-400" />
                            <span className="ml-2 text-xl font-bold">EduLearn</span>
                        </div>
                        <p className="text-gray-400 mb-4">
                            Nền tảng học trực tuyến hàng đầu Việt Nam, mang đến trải nghiệm học tập tốt nhất.
                        </p>
                        <div className="flex space-x-4">
                            <a href="#" className="text-gray-400 hover:text-white">Facebook</a>
                            <a href="#" className="text-gray-400 hover:text-white">YouTube</a>
                            <a href="#" className="text-gray-400 hover:text-white">LinkedIn</a>
                        </div>
                    </div>

                    {/* Courses */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Khóa học</h3>
                        <ul className="space-y-2">
                            <li><a href="#" className="text-gray-400 hover:text-white">Lập trình</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Thiết kế</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Marketing</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Kinh doanh</a></li>
                        </ul>
                    </div>

                    {/* Support */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Hỗ trợ</h3>
                        <ul className="space-y-2">
                            <li><a href="#" className="text-gray-400 hover:text-white">Trung tâm trợ giúp</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Liên hệ</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Điều khoản</a></li>
                            <li><a href="#" className="text-gray-400 hover:text-white">Chính sách</a></li>
                        </ul>
                    </div>

                    {/* Contact */}
                    <div>
                        <h3 className="text-lg font-semibold mb-4">Liên hệ</h3>
                        <ul className="space-y-2 text-gray-400">
                            <li>Email: support@edulearn.vn</li>
                            <li>Hotline: 1900 1234</li>
                            <li>Địa chỉ: 123 Nguyễn Văn Cừ, Q.1, TP.HCM</li>
                        </ul>
                    </div>
                </div>

                <div className="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
                    <p>&copy; 2024 EduLearn. Tất cả quyền được bảo lưu.</p>
                </div>
            </div>
        </footer>
    )
}
