import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Order_manage_sys {
    static int count = 0;

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("-------登录-------");
            System.out.println("请输入管理员用户：");//用户名：ZX
            String name = sc.nextLine();
            System.out.println("请输入密码：");//密码：123456
            String password = sc.nextLine();
            login(name, password);
            if (count == 5) {
                for (; ; ) {
                    System.out.println("=======订单管理系统=====");
                    System.out.println("------1.增添订单--");
                    System.out.println("------2.订单删除--");
                    System.out.println("------3.订单修改--");
                    System.out.println("------4.订单查询--");
                    System.out.println("------5.上架商品--");
                    System.out.println("---------6.退出---");
                    System.out.println("====请输入你要执行的操作====");

                    int option = sc.nextInt();
                    switch (option) {
                        case 1:
                            addOrder();
                            break;
                        case 2:
                            deleteOrder();
                            break;
                        case 3:
                            alterOrder();
                            break;
                        case 4:
                            findOrder();
                            break;
                        case 5:
                            addGoods();
                            break;
                        case 6:
                            System.exit(0);
                    }

                }
            }
            count++;
            if (count > 3) {
                System.out.println("账号被锁定");
                break;
            }
        }
    }

    /**
     * 登录业务
     */
    public static void login(String userName, String password) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils_0.getConnection();
            String sql = "SELECT * FROM manager WHERE `manager_name`=? AND `password`=?";
            st = con.prepareStatement(sql);
            st.setString(1, userName);
            st.setString(2, password);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("登录成功");
                count = 5;
            } else {
                System.out.println("登录失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_0.release(con, st, rs);//释放
        }
    }

    public static void findOrder() {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            System.out.println("是否分页查询");
            if (!sc.nextBoolean()) {//输入false进入
                con = JDBCUtils_0.getConnection();
                //使用?占位符代替参数
                String sql = "SELECT * FROM order_information WHERE order_id=?";
                //预编译SQL，先写SQL，然后不执行
                st = con.prepareStatement(sql);
                System.out.println("请输入你要查询的订单号：");
                int number = sc.nextInt();
                st.setInt(1, number);
                rs = st.executeQuery();
                //判断订单是否存在
                int judge = 0;
                while (rs.next()) {
                    System.out.println("订单号：" + rs.getInt("order_id"));
                    System.out.println("商品编号：" + rs.getString("goods_id"));
                    System.out.println("订单时间：" + rs.getDate("order_time"));
                    judge++;
                }
                if (judge == 0) {
                    System.out.println("订单不存在，查询失败");
                }
            } else {//输入true进入
                con = JDBCUtils_0.getConnection();
                //使用?占位符代替参数
                String sql = "SELECT * FROM order_information limit ?,5";
                //预编译SQL，先写SQL，然后不执行
                st = con.prepareStatement(sql);
                System.out.println("输入你要查询的页码：");//
                int number1 = sc.nextInt();
                st.setInt(1,(number1-1)*5);
                rs = st.executeQuery();
                //判断订单是否存在
                while (rs.next()) {
                    System.out.println("订单号：" + rs.getInt("order_id"));
                    System.out.println("商品编号：" + rs.getString("goods_id"));
                    System.out.println("下单日期：" + rs.getDate("order_time"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_0.release(con, st, rs);
        }
    }

    public static void addOrder() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        try {
            con = JDBCUtils_0.getConnection();
            //使用?占位符代替参数
            String sql = "INSERT INTO order_information(`goods_id`,`order_time`) VALUES (?,?)";
            String sql1 = "SELECT * FROM goods_information WHERE goods_id = ?";
            //预编译SQL，先写SQL，然后不执行
            st = con.prepareStatement(sql1);
            //手动给参数赋值
            System.out.println("请输入下单的商品编号");
            int str = sc.nextInt();
            st.setInt(1, str);
            rs = st.executeQuery();
            if (rs.next()) {
                st = con.prepareStatement(sql);
                st.setInt(1, str);
                st.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                int num = st.executeUpdate();
                if (num > 0) {
                    System.out.println("增加订单成功！");
                }
            } else System.out.println("该商品不存在，下单失败");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_0.release(con, st, rs);
        }
    }

    public static void deleteOrder() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        try {
            con = JDBCUtils_0.getConnection();
            //使用?占位符代替参数
            String sql = "DELETE FROM order_information WHERE `order_id`=?";
            String sql0 = "SELECT * FROM order_information WHERE `order_id`=?";
            //预编译SQL，先写SQL，然后不执行
            st = con.prepareStatement(sql0);
            System.out.println("请输入要取消的订单");//先查询后删除
            int num1 = sc.nextInt();
            st.setInt(1, num1);
            rs = st.executeQuery();
            if (rs.next()) {
                st = con.prepareStatement(sql);
                st.setInt(1, num1);
                st.execute();
                System.out.println("删除成功");
            } else {
                System.out.println("订单不存在，删除失败");
            }
        } catch (SQLException e) {
            System.out.println("订单不存在，删除失败");
        } finally {
            JDBCUtils_0.release(con, st, rs);
        }

    }

    public static void alterOrder() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        try {
            con = JDBCUtils_0.getConnection();
            //使用?占位符代替参数
            String sql = "UPDATE order_information SET goods_id=?,order_time=? WHERE order_id=?";
            String sql0 = "SELECT * FROM order_information WHERE `order_id`=?";
            //预编译SQL，先写SQL，然后不执行
            st = con.prepareStatement(sql0);
            //手动给参数赋值
            System.out.println("请输入要修改的订单单号");
            int num2 = sc.nextInt();
            st.setInt(1, num2);
            rs = st.executeQuery();
            if (rs.next()) {

                st = con.prepareStatement(sql);
                st.setInt(3, num2);
                System.out.println("请输入修改后的商品编号：");
                int num3 = sc.nextInt();
                st.setInt(1, num3);
                System.out.println("请输入修改后的订单日期");//yyyy-MM-dd HH:mm:ss;
                String str = sc.next();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date001 = sdf.parse(str);
                    java.sql.Date date002 = new java.sql.Date(date001.getTime());
                    st.setDate(2, date002);
                    st.execute();
                    System.out.println("修改成功！");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("订单不存在，无法修改");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_0.release(con, st, rs);
        }
    }

    public static void addGoods() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        try {
            con = JDBCUtils_0.getConnection();
            //使用?占位符代替参数
            String sql = "INSERT INTO goods_information(`goods_name`,`goods_price`) VALUES (?,?)";
            String sql0 = "SELECT * FROM goods_information WHERE `goods_name`= ?";
            //预编译SQL，先写SQL，然后不执行
            st = con.prepareStatement(sql0);
            System.out.println("请输入上架商品的名称");
            String str = sc.next();
            st.setString(1, str);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("商品名称重复，上架失败");
            } else {
                st = con.prepareStatement(sql);
                st.setString(1, str);
                System.out.println("请输入商品单价");
                st.setFloat(2, sc.nextFloat());
                int num = st.executeUpdate();
                if (num > 0) {
                    System.out.println("商品上架成功");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_0.release(con, st, rs);
        }
    }
}
