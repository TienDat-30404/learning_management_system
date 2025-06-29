import React from 'react';

const WelcomeSection: React.FC = () => {
    const stats = [
        { number: '10,000+', label: 'Khóa học', icon: '📚' },
        { number: '2M+', label: 'Học viên', icon: '👥' },
        { number: '50+', label: 'Ngôn ngữ', icon: '🌍' },
        { number: '95%', label: 'Hài lòng', icon: '⭐' }
    ];

    return (
        <section className="py-16 bg-white">
            <div className="container mx-auto px-4">
                {/* Thống kê tổng quan */}
                <div className="grid grid-cols-2 md:grid-cols-4 gap-8 mb-16">
                    {stats.map((stat, index) => (
                        <div key={index} className="text-center">
                            <div className="text-4xl mb-2">{stat.icon}</div>
                            <div className="text-3xl font-bold text-gray-900 mb-1">{stat.number}</div>
                            <div className="text-gray-600">{stat.label}</div>
                        </div>
                    ))}
                </div>

                {/* Tại sao chọn chúng tôi */}
                <div className="max-w-6xl mx-auto">
                    <h2 className="text-4xl font-bold text-center text-gray-900 mb-4">
                        Tại Sao Chọn Nền Tảng Của Chúng Tôi?
                    </h2>
                    <p className="text-xl text-gray-600 text-center mb-12">
                        Chúng tôi cam kết mang đến trải nghiệm học tập tốt nhất cho bạn
                    </p>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        <div className="bg-blue-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-blue-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Học Mọi Lúc, Mọi Nơi</h3>
                            <p className="text-gray-600">
                                Truy cập khóa học 24/7 trên mọi thiết bị. Học theo tốc độ của riêng bạn.
                            </p>
                        </div>

                        <div className="bg-green-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-green-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Giảng Viên Chuyên Nghiệp</h3>
                            <p className="text-gray-600">
                                Học từ các chuyên gia hàng đầu trong từng lĩnh vực với kinh nghiệm thực tế.
                            </p>
                        </div>

                        <div className="bg-purple-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-purple-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Dự Án Thực Tế</h3>
                            <p className="text-gray-600">
                                Áp dụng kiến thức vào các dự án thực tế để xây dựng portfolio ấn tượng.
                            </p>
                        </div>

                        <div className="bg-yellow-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-yellow-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Cộng Đồng Hỗ Trợ</h3>
                            <p className="text-gray-600">
                                Tham gia cộng đồng học viên tích cực, chia sẻ kinh nghiệm và giải đáp thắc mắc.
                            </p>
                        </div>

                        <div className="bg-red-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-red-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Chứng Chỉ Được Công Nhận</h3>
                            <p className="text-gray-600">
                                Nhận chứng chỉ hoàn thành khóa học được các nhà tuyển dụng tin tưởng.
                            </p>
                        </div>

                        <div className="bg-indigo-50 p-6 rounded-lg">
                            <div className="w-12 h-12 bg-indigo-600 rounded-lg flex items-center justify-center mb-4">
                                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                                </svg>
                            </div>
                            <h3 className="text-xl font-semibold mb-3">Theo Dõi Tiến Độ</h3>
                            <p className="text-gray-600">
                                Hệ thống theo dõi tiến độ học tập chi tiết giúp bạn luôn có động lực.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default WelcomeSection;