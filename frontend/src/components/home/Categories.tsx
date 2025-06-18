// 'use client';

// import { useEffect, useState } from 'react';
// import { fetchCategories, Category } from '@/app/api/categories';

// export default function Categories() {
//   const [categories, setCategories] = useState<Category[]>([]);

//   useEffect(() => {
//     const getData = async () => {
//       const data = await fetchCategories();
//       setCategories(data);
//     };
//     getData();
//   }, []);

//   return (
//     <section className="py-12">
//       <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
//         <h2 className="text-2xl font-bold text-gray-900 mb-8 text-center">
//           Danh mục khóa học
//         </h2>
//         <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
//           {categories.map((category) => (
//             <button
//               key={category.id}
//               className="p-4 bg-white rounded-lg shadow hover:shadow-md transition text-center"
//             >
//               <div className="text-blue-600 font-medium">{category.name}</div>
//             </button>
//           ))}
//         </div>
//       </div>
//     </section>
//   );
// }
