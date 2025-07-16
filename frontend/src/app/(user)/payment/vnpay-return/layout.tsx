export default function VnpayReturnLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="vnpay-return-layout" style={{ border: '2px solid red' }}>
      {children}
    </div>
  );
}