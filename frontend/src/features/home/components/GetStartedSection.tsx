const GetStartedSection: React.FC = () => {
    const steps = [
        {
            step: '01',
            title: 'Tạo Tài Khoản',
            description: 'Đăng ký miễn phí chỉ trong vài giây với email hoặc tài khoản mạng xã hội',
            icon: (
                <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                </svg>
            )
        },
        {
            step: '02',
            title: 'Chọn Khóa Học',
            description: 'Duyệt qua hàng nghìn khóa học và tìm những gì phù hợp với mục tiêu của bạn',
            icon: (
                <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                </svg>
            )
        },
        {
            step: '03',
            title: 'Bắt Đầu Học',
            description: 'Học theo tốc độ riêng của bạn với video chất lượng cao và bài tập thực hành',
            icon: (
                <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1m4 0h1m-6 4h8m-6-4h.01M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                </svg>
            )
        },
        {
            step: '04',
            title: 'Nhận Chứng Chỉ',
            description: 'Hoàn thành khóa học và nhận chứng chỉ để nâng cao profile nghề nghiệp',
            icon: (
                <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
            )
        }
    ];


    return (
        <section className="py-16 bg-gray-50">
            <div className="container mx-auto px-4">
                {/* Hướng dẫn bắt đầu */}
                <div className="text-center mb-16">
                    <h2 className="text-4xl font-bold text-gray-900 mb-4">
                        Bắt Đầu Hành Trình Học Tập
                    </h2>
                    <p className="text-xl text-gray-600 max-w-2xl mx-auto">
                        Chỉ với 4 bước đơn giản, bạn đã có thể bắt đầu học những kỹ năng mới ngay hôm nay
                    </p>
                </div>

                {/* Các bước */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-16">
                    {steps.map((item, index) => (
                        <div key={index} className="text-center">
                            <div className="relative mb-6">
                                <div className="w-20 h-20 bg-blue-600 rounded-full flex items-center justify-center text-white mx-auto mb-4">
                                    {item.icon}
                                </div>
                                <div className="absolute -top-2 -right-2 w-8 h-8 bg-yellow-400 rounded-full flex items-center justify-center text-sm font-bold text-gray-900">
                                    {item.step}
                                </div>
                            </div>
                            <h3 className="text-xl font-semibold text-gray-900 mb-3">{item.title}</h3>
                            <p className="text-gray-600">{item.description}</p>
                        </div>
                    ))}
                </div>

            </div>
        </section>
    );
};

export default GetStartedSection;