function DashboardCard({ title, value }) {
  return (
    <div style={cardStyle}>
      <h3 style={{ margin: 0 }}>{title}</h3>
      <h2 style={{ margin: '8px 0 0 0' }}>₹ {(Number(value) || 0).toFixed(2)}</h2>
    </div>
  );
}

const cardStyle = {
  background: "#ffffff",
  padding: "20px",
  borderRadius: "10px",
  boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
  flex: 1,
  color: '#111827'
};

export default DashboardCard;