'use client';

import { Provider } from 'react-redux';
import { store, persistor } from '@/store';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './globals.css';
import { useState } from 'react';
import { PersistGate } from 'redux-persist/integration/react';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const [queryClient] = useState(() => new QueryClient());

  return (
    <html lang="en">
      <body className="min-h-screen bg-gray-50">
        <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
            <QueryClientProvider client={queryClient}>
              {children}
            </QueryClientProvider>
          </PersistGate>
        </Provider>
      </body>
    </html>
  );
}
