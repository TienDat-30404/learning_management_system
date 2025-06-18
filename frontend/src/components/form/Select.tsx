'use client'

import React from 'react';
import { AlertCircle } from 'lucide-react';

interface SelectWithIconProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
  label: string;
  icon: React.ReactNode;
  error?: string;
  extraRightButton?: React.ReactNode;
  options: { label: string; value: string }[];
}

const SelectWithIcon: React.FC<SelectWithIconProps> = ({
  label,
  icon,
  error,
  extraRightButton,
  options,
  ...selectProps
}) => {
  return (
    <div className="space-y-2">
      <label className="text-sm font-medium text-gray-700">{label}</label>
      <div className="relative">
        <div className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400">
          {icon}
        </div>

        <select
          {...selectProps}
          className={`block w-full pl-10 pr-3 py-3 border rounded-xl shadow-sm bg-white text-gray-700
            placeholder-gray-400 border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all
            ${error ? 'border-red-800' : ''}
          `}
        >
          <option value="">-- Chọn một mục --</option>
          {options.map(opt => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>

        {extraRightButton && (
          <div className="absolute right-3 top-1/2 transform -translate-y-1/2">
            {extraRightButton}
          </div>
        )}
      </div>

      {error && (
        <p className="text-sm text-red-600 flex items-center space-x-1 mt-1">
          <AlertCircle className="w-4 h-4" />
          <span>{error}</span>
        </p>
      )}
    </div>
  );
};

export default SelectWithIcon;
