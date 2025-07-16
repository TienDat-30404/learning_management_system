
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
// Đường dẫn import thay đổi để trỏ đúng từ src/hooks đến src/app/store
import { RootState, AppDispatch } from '@/store';
export const useAppDispatch: () => AppDispatch = useDispatch;


export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;