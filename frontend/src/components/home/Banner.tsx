import React from 'react';
import Link from 'next/link';

const Banner: React.FC = () => {
    return (
        <section className="relative bg-gradient-to-r from-blue-600 to-purple-600 text-white">
            <div className="container mx-auto px-4 py-20">
                <div className="max-w-4xl mx-auto text-center">
                    <h1 className="text-5xl md:text-6xl font-bold mb-6">
                        Khám Phá Thế Giới 
                        <span className="text-yellow-300"> Học Tập</span>
                    </h1>
                    
                    <p className="text-xl md:text-2xl mb-8 text-blue-100">
                        Hàng nghìn khóa học chất lượng cao, được thiết kế bởi các chuyên gia 
                        hàng đầu để giúp bạn phát triển kỹ năng và sự nghiệp.
                    </p>
                    
                    <div className="flex flex-col sm:flex-row gap-4 justify-center items-center mb-12">
                        <Link 
                            href="/register" 
                            className="bg-yellow-400 hover:bg-yellow-500 text-gray-900 font-semibold px-8 py-4 rounded-lg text-lg transition-colors duration-200"
                        >
                            Đăng Ký Miễn Phí
                        </Link>
                        
                        <Link 
                            href="/courses" 
                            className="border-2 border-white hover:bg-white hover:text-blue-600 font-semibold px-8 py-4 rounded-lg text-lg transition-colors duration-200"
                        >
                            Xem Khóa Học
                        </Link>
                    </div>
                    
                    {/* Tính năng nổi bật */}
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-16">
                        <div className="text-center">
                            <div className="bg-white bg-opacity-20 rounded-full w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                                <svg className="w-8 h-8" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>
                            </div>
                            <h3 className="font-semibold text-lg mb-2">Chứng Chỉ Uy Tín</h3>
                            <p className="text-blue-100">Nhận chứng chỉ được công nhận trong ngành</p>
                        </div>
                        
                        <div className="text-center">
                            <div className="bg-white bg-opacity-20 rounded-full w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                                <svg className="w-8 h-8" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M13 6a3 3 0 11-6 0 3 3 0 016 0zM18 8a2 2 0 11-4 0 2 2 0 014 0zM14 15a4 4 0 00-8 0v3h8v-3z" />
                                </svg>
                            </div>
                            <h3 className="font-semibold text-lg mb-2">Cộng Đồng Hỗ Trợ</h3>
                            <p className="text-blue-100">Kết nối với hàng triệu học viên trên toàn cầu</p>
                        </div>
                        
                        <div className="text-center">
                            <div className="bg-white bg-opacity-20 rounded-full w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                                <svg className="w-8 h-8" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M12 2l3.09 6.26L22 9l-5 4.87L18.18 22 12 18.77 5.82 22 7 13.87 2 9l6.91-.74L12 2z" />
                                </svg>
                            </div>
                            <h3 className="font-semibold text-lg mb-2">Chất Lượng Cao</h3>
                            <p className="text-blue-100">Nội dung được cập nhật liên tục</p>
                        </div>
                    </div>
                </div>
            </div>
            
            {/* Decorative elements */}
            <div className="absolute top-0 right-0 w-20 h-26 bg-white bg-opacity-10 rounded-full -mt-16"></div>
            <div className="absolute bottom-0 left-0 w-24 h-24 bg-white bg-opacity-10 rounded-full -ml-12 -mb-12"></div>
        </section>
    );
};

export default Banner;