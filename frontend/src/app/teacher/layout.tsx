'use client';

import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function TeacherLayout({ children }: { children: React.ReactNode }) {
  const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user);
  console.log(userInfo?.role)
  const router = useRouter();

  useEffect(() => {
    if (!isAuthenticated || userInfo?.role !== 'Teacher') {
      router.push('/'); 
    }
  }, [isAuthenticated, userInfo, router]);

  if (!isAuthenticated || userInfo?.role !== 'Teacher') {
    return null;
  }

  return <>{children}</>;
}
