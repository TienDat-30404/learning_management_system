
import {  BookOpen, Star, Clock, Award } from 'lucide-react';

interface Stat {
    label: string;
    value: string;
    icon: React.ReactNode;
}

export default function StatsSection() {
    const stats: Stat[] = [
        {
            label: "Khóa học đã hoàn thành",
            value: "12",
            icon: <Award className="w-6 h-6 text-green-500" />
        },
        {
            label: "Giờ học",
            value: "156",
            icon: <Clock className="w-6 h-6 text-blue-500" />
        },
        {
            label: "Chứng chỉ",
            value: "8",
            icon: <BookOpen className="w-6 h-6 text-purple-500" />
        },
        {
            label: "Điểm trung bình",
            value: "8.9",
            icon: <Star className="w-6 h-6 text-yellow-500" />
        }
    ];
    return (
        <section className="py-12 bg-white">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
                    {stats.map((stat, index) => (
                        <div key={index} className="text-center p-6 bg-gray-50 rounded-lg">
                            <div className="flex justify-center mb-3">
                                {stat.icon}
                            </div>
                            <div className="text-2xl font-bold text-gray-900 mb-1">{stat.value}</div>
                            <div className="text-sm text-gray-600">{stat.label}</div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    )
}