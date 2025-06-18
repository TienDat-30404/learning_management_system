export default function HeroSection() {
    return (
        <section className="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-16">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="text-center">
                    <h1 className="text-4xl md:text-5xl font-bold mb-4">
                        Chào mừng trở lại, Nguyễn Văn Nam!
                    </h1>
                    <p className="text-xl mb-8 text-blue-100">
                        Tiếp tục hành trình học tập của bạn và đạt được mục tiêu mới
                    </p>
                    <div className="flex flex-col sm:flex-row gap-4 justify-center">
                        <button className="bg-white text-blue-600 px-8 py-3 rounded-lg font-semibold hover:bg-gray-100 transition">
                            Tiếp tục học
                        </button>
                        <button className="border-2 border-white text-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-blue-600 transition">
                            Khám phá khóa học mới
                        </button>
                    </div>
                </div>
            </div>
        </section>
    )
}