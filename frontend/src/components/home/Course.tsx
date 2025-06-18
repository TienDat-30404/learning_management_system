
import { Star, Clock, Users, ChevronRight } from 'lucide-react';

interface Course {
    id: number;
    title: string;
    instructor: string;
    rating: number;
    students: number;
    duration: string;
    image: string;
    progress?: number;
    category: string;
}

export default function Course() {


    const featuredCourses: Course[] = [
        {
            id: 1,
            title: "Lập trình JavaScript Cơ bản",
            instructor: "Nguyễn Văn A",
            rating: 4.8,
            students: 1234,
            duration: "12 giờ",
            image: "https://images.unsplash.com/photo-1627398242454-45a1465c2479?w=300&h=200&fit=crop",
            progress: 60,
            category: "Lập trình"
        },
        {
            id: 2,
            title: "Thiết kế UI/UX Chuyên nghiệp",
            instructor: "Trần Thị B",
            rating: 4.9,
            students: 987,
            duration: "15 giờ",
            image: "https://images.unsplash.com/photo-1561070791-2526d30994b5?w=300&h=200&fit=crop",
            category: "Thiết kế"
        },
        {
            id: 3,
            title: "Marketing Digital 2024",
            instructor: "Lê Văn C",
            rating: 4.7,
            students: 2156,
            duration: "10 giờ",
            image: "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=300&h=200&fit=crop",
            category: "Marketing"
        }
    ];
    return (
        <section className="py-12 bg-gray-50">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center mb-8">
                    <h2 className="text-2xl font-bold text-gray-900">Khóa học nổi bật</h2>
                    <a href="#" className="text-blue-600 hover:text-blue-700 flex items-center">
                        Xem tất cả <ChevronRight className="w-4 h-4 ml-1" />
                    </a>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {featuredCourses.map((course) => (
                        <div key={course.id} className="bg-white rounded-lg shadow hover:shadow-md transition">
                            <div className="relative">
                                <img
                                    src={course.image}
                                    alt={course.title}
                                    className="w-full h-48 object-cover rounded-t-lg"
                                />
                                <div className="absolute top-2 right-2">
                                    <span className="bg-blue-600 text-white px-2 py-1 rounded text-xs">
                                        {course.category}
                                    </span>
                                </div>
                            </div>
                            <div className="p-6">
                                <h3 className="font-semibold text-lg mb-2">{course.title}</h3>
                                <p className="text-gray-600 text-sm mb-3">Giảng viên: {course.instructor}</p>
                                <div className="flex items-center justify-between">
                                    <div className="flex items-center space-x-4 text-sm text-gray-500">
                                        <div className="flex items-center">
                                            <Star className="w-4 h-4 text-yellow-400 mr-1" />
                                            {course.rating}
                                        </div>
                                        <div className="flex items-center">
                                            <Users className="w-4 h-4 mr-1" />
                                            {course.students}
                                        </div>
                                        <div className="flex items-center">
                                            <Clock className="w-4 h-4 mr-1" />
                                            {course.duration}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    )
}