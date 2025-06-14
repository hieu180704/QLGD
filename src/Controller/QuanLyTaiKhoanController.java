package Controller;

import Model.TaiKhoan;
import Service.TaiKhoanService;
import View.Admin.QuanLyTaiKhoan.QuanLyTaiKhoanPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyTaiKhoanController {

    private QuanLyTaiKhoanPanel view;
    private TaiKhoanService service;

    public QuanLyTaiKhoanController(QuanLyTaiKhoanPanel view) {
        this.view = view;
        this.service = new TaiKhoanService();
        initController();
    }

    private void initController() {
        // Xử lý nút Thêm
        if (view.getBtnAdd().getActionListeners().length > 0) {
            view.getBtnAdd().removeActionListener(view.getBtnAdd().getActionListeners()[0]);
        }
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddButton();
            }
        });

        // Xử lý nút Cập nhật
        if (view.getBtnUpdate().getActionListeners().length > 0) {
            view.getBtnUpdate().removeActionListener(view.getBtnUpdate().getActionListeners()[0]);
        }
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateButton();
            }
        });

        // Xử lý nút Xóa
        if (view.getBtnDelete().getActionListeners().length > 0) {
            view.getBtnDelete().removeActionListener(view.getBtnDelete().getActionListeners()[0]);
        }
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteButton();
            }
        });

        // Xử lý nút Tìm kiếm
        if (view.getBtnSearch().getActionListeners().length > 0) {
            view.getBtnSearch().removeActionListener(view.getBtnSearch().getActionListeners()[0]);
        }
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchButton();
            }
        });

        // Xử lý sự kiện click vào bảng
        for (MouseListener listener : view.getTable().getMouseListeners()) {
            view.getTable().removeMouseListener(listener);
        }
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableClick(e);
            }
        });

        // Xử lý nút Xuất Excel
        view.getBtnExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        // Xử lý nút Nhập Excel
        view.getBtnImport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromExcel();
            }
        });
    }

    private void handleAddButton() {
        String tenDangNhap = view.getTxtTenDangNhap().getText().trim();
        String matKhau = view.getTxtMatKhau().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        int loaiTaiKhoan = 1; // Mặc định Admin

        TaiKhoan tk = new TaiKhoan(0, tenDangNhap, matKhau, email, loaiTaiKhoan);
        System.out.println("Thêm tài khoản: " + tenDangNhap); // Log
        if (service.addTaiKhoan(tk)) {
            view.showMessage("Thêm tài khoản thành công!");
            view.loadData();
            view.clearInput();
        } else {
            view.showMessage("Thêm tài khoản thất bại! Kiểm tra lại thông tin (tên đăng nhập không được trùng, các trường không được rỗng, độ dài hợp lệ).");
        }
    }

    private void handleUpdateButton() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            view.showMessage("Vui lòng chọn một tài khoản để cập nhật!");
            return;
        }

        int maTaiKhoan = (int) view.getModel().getValueAt(selectedRow, 0);
        String tenDangNhap = (String) view.getModel().getValueAt(selectedRow, 1); // Lấy từ bảng
        String matKhau = view.getTxtMatKhau().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        int loaiTaiKhoan = 1; // Mặc định Admin

        TaiKhoan tk = new TaiKhoan(maTaiKhoan, tenDangNhap, matKhau, email, loaiTaiKhoan);
        System.out.println("Cập nhật tài khoản: " + maTaiKhoan); // Log
        if (service.updateTaiKhoan(tk)) {
            view.showMessage("Cập nhật tài khoản thành công!");
            view.loadData();
            view.clearInput();
        } else {
            view.showMessage("Cập nhật tài khoản thất bại! Kiểm tra lại thông tin (các trường không được rỗng, độ dài hợp lệ).");
        }
    }

    private void handleDeleteButton() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            view.showMessage("Vui lòng chọn một tài khoản để xóa!");
            return;
        }

        int maTaiKhoan = (int) view.getModel().getValueAt(selectedRow, 0);
        System.out.println("Xóa tài khoản: " + maTaiKhoan); // Log
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteTaiKhoan(maTaiKhoan)) {
                view.showMessage("Xóa tài khoản thành công!");
                view.loadData();
                view.clearInput();
            } else {
                view.showMessage("Xóa tài khoản thất bại! Mã tài khoản không hợp lệ.");
            }
        }
    }

    private void handleSearchButton() {
        String searchText = view.getTxtSearch().getText().trim();
        System.out.println("Tìm kiếm: " + searchText); // Log
        view.displayData(service.findTaiKhoanByUsername(searchText));
    }

    private void handleTableClick(MouseEvent e) {
        int selectedRow = view.getTable().getSelectedRow();
        System.out.println("Dòng được chọn: " + selectedRow); // Log chi tiết
        if (selectedRow != -1) {
            try {
                view.getTxtTenDangNhap().setText((String) view.getModel().getValueAt(selectedRow, 1));
                view.getTxtTenDangNhap().setEnabled(false); // Khóa trường Tên Đăng Nhập
                view.getTxtMatKhau().setText((String) view.getModel().getValueAt(selectedRow, 2));
                view.getTxtEmail().setText((String) view.getModel().getValueAt(selectedRow, 3));
                String loaiTaiKhoan = (String) view.getModel().getValueAt(selectedRow, 4);
                view.getCboLoaiTaiKhoan().setSelectedIndex(0); // Chỉ có Admin
                System.out.println("Chọn tài khoản: " + view.getModel().getValueAt(selectedRow, 0)); // Log
            } catch (Exception ex) {
                System.out.println("Lỗi khi chọn dòng: " + ex.getMessage()); // Log lỗi
            }
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("TaiKhoan.xlsx"));
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream(fileToSave)) {

                Sheet sheet = workbook.createSheet("Danh Sách Tài Khoản");
                DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
                Row headerRow = sheet.createRow(0);

                // Tạo header và đặt kiểu dữ liệu
                CellStyle textStyle = workbook.createCellStyle();
                textStyle.setDataFormat(workbook.createDataFormat().getFormat("@")); // Định dạng Text
                CellStyle numberStyle = workbook.createCellStyle();
                DataFormat numberFormat = workbook.createDataFormat();
                numberStyle.setDataFormat(numberFormat.getFormat("0")); // Định dạng số nguyên

                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                    if (i == 0) { // Cột 0 (Mã Tài Khoản)
                        cell.setCellStyle(numberStyle);
                    } else { // Cột 1, 2, 3, 4 (Tên Đăng Nhập, Mật Khẩu, Email, Loại Tài Khoản)
                        cell.setCellStyle(textStyle);
                    }
                }

                // Điền dữ liệu từ bảng với kiểu phù hợp
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        Cell cell = row.createCell(j);
                        if (value != null) {
                            String valueStr = value.toString();
                            if (j == 0) { // Cột 0 (Mã Tài Khoản)
                                try {
                                    cell.setCellValue(Integer.parseInt(valueStr));
                                    cell.setCellStyle(numberStyle);
                                } catch (NumberFormatException e) {
                                    cell.setCellValue(valueStr);
                                    cell.setCellStyle(textStyle);
                                }
                            } else { // Cột 1, 2, 3, 4 là văn bản
                                cell.setCellValue(valueStr);
                                cell.setCellStyle(textStyle);
                            }
                        }
                    }
                }

                workbook.write(outputStream);
                view.showMessage("Xuất file Excel thành công: " + fileToSave.getAbsolutePath());
            } catch (Exception e) {
                view.showMessage("Lỗi khi xuất file Excel: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        int userSelection = fileChooser.showOpenDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try (FileInputStream inputStream = new FileInputStream(fileToImport); Workbook workbook = new XSSFWorkbook(inputStream)) {

                Sheet sheet = workbook.getSheetAt(0);
                DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
                model.setRowCount(0);
                Iterator<Row> rowIterator = sheet.iterator();

                // Bỏ qua header
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                List<TaiKhoan> importedTaiKhoanList = new ArrayList<>();
                List<TaiKhoan> existingTaiKhoanList = service.getAllTaiKhoan(); // Lấy dữ liệu hiện có từ DB
                int rowIndex = 1; // Bắt đầu từ dòng 1 (sau header)

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) {
                        TaiKhoan tk = new TaiKhoan();
                        tk.setTendangnhap(row.getCell(1).getStringCellValue());
                        tk.setMatkhau(row.getCell(2).getStringCellValue());
                        tk.setEmail(row.getCell(3).getStringCellValue());
                        String loaiTaiKhoanStr = row.getCell(4).getStringCellValue();
                        int loaiTaiKhoan = loaiTaiKhoanStr.equals("Admin") ? 1 : 0; // Giả định 1 là Admin, 0 là User
                        tk.setLoaitaikhoan(loaiTaiKhoan);

                        // Kiểm tra trùng lặp với dữ liệu hiện có trong DB
                        boolean isDuplicate = false;
                        for (TaiKhoan existingTk : existingTaiKhoanList) {
                            if (existingTk.getTendangnhap().equalsIgnoreCase(tk.getTendangnhap())) {
                                isDuplicate = true;
                                System.out.println("Bỏ qua dòng " + (rowIndex + 1) + ": Trùng Tên Đăng Nhập - TenDangNhap: " + tk.getTendangnhap());
                                break;
                            }
                        }
                        if (!isDuplicate) {
                            importedTaiKhoanList.add(tk);
                            model.addRow(new Object[]{null, tk.getTendangnhap(), tk.getMatkhau(), tk.getEmail(), loaiTaiKhoan == 1 ? "Admin" : "User"});
                        }
                        rowIndex++;
                    }
                }

                // Lưu vào cơ sở dữ liệu
                for (TaiKhoan tk : importedTaiKhoanList) {
                    System.out.println("Thử thêm tài khoản dòng " + rowIndex + ": " + tk.getTendangnhap());
                    if (service.addTaiKhoan(tk)) {
                        System.out.println("Thêm thành công dòng " + rowIndex + ": " + tk.getTendangnhap());
                    } else {
                        view.showMessage("Lỗi khi thêm tài khoản từ file dòng " + rowIndex + ": " + tk.getTendangnhap() + " (Kiểm tra thông tin)");
                    }
                }
                view.loadData(); // Cập nhật lại bảng với dữ liệu từ DB
                view.showMessage("Nhập file Excel thành công: " + fileToImport.getAbsolutePath());
            } catch (Exception e) {
                view.showMessage("Lỗi khi nhập file Excel: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
