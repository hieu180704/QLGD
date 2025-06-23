package Controller;

import Model.SoDo;
import Model.DoiBong;
import Service.SoDoService;
import View.Admin.QuanLySoDo.QuanLySoDoPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class SoDoController {
    private QuanLySoDoPanel view;
    private SoDoService service;

    public SoDoController(QuanLySoDoPanel view) {
        this.view = view;
        this.service = new SoDoService();
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Gán sự kiện cho các nút
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSoDoRaSan();
            }
        });
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSoDoRaSan();
            }
        });
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSoDoRaSan();
            }
        });
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSoDoRaSan();
            }
        });

        // Gán sự kiện click vào bảng
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableClick(e);
            }
        });

        // Gán sự kiện cho nút Xuất Excel
        view.getBtnExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        // Gán sự kiện cho nút Nhập Excel
        view.getBtnImport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromExcel();
            }
        });
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện tại
        for (SoDo soDo : service.getAllSoDoRaSan()) {
            model.addRow(new Object[]{soDo.getMaSoDo(), soDo.getTenSoDo(), soDo.getLoaiSoDo(), soDo.getChienThuat(), soDo.getMaDoiBong()});
        }
    }

    private void addSoDoRaSan() {
        try {
            String tenSoDo = view.getTxtTenSoDo().getText().trim();
            String loaiSoDo = view.getTxtLoaiSoDo().getText().trim();
            String chienThuat = view.getTxtChienThuat().getText().trim();
            DoiBong selectedDoiBong = (DoiBong) view.getCboMaDoiBong().getSelectedItem();
            if (selectedDoiBong == null || tenSoDo.isEmpty() || loaiSoDo.isEmpty() || chienThuat.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hợp lệ!");
                return;
            }
            int maDoiBong = selectedDoiBong.getMaDoiBong();
            SoDo soDo = new SoDo();
            soDo.setTenSoDo(tenSoDo);
            soDo.setLoaiSoDo(loaiSoDo);
            soDo.setChienThuat(chienThuat);
            soDo.setMaDoiBong(maDoiBong);
            if (service.addSoDoRaSan(soDo)) {
                JOptionPane.showMessageDialog(view, "Thêm sơ đồ ra sân thành công!");
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sơ đồ ra sân thất bại! (Trùng tên, độ dài > 50 ký tự, hoặc maDoiBong không hợp lệ)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm sơ đồ: " + e.getMessage());
        }
        loadData();
    }

    private void updateSoDoRaSan() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int maSoDo = (int) view.getTable().getValueAt(selectedRow, 0);
                String tenSoDo = view.getTxtTenSoDo().getText().trim();
                String loaiSoDo = view.getTxtLoaiSoDo().getText().trim();
                String chienThuat = view.getTxtChienThuat().getText().trim();
                DoiBong selectedDoiBong = (DoiBong) view.getCboMaDoiBong().getSelectedItem();
                if (selectedDoiBong == null || tenSoDo.isEmpty() || loaiSoDo.isEmpty() || chienThuat.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hợp lệ!");
                    return;
                }
                int maDoiBong = selectedDoiBong.getMaDoiBong();
                SoDo soDo = new SoDo(maSoDo, tenSoDo, loaiSoDo, chienThuat, maDoiBong);
                if (service.updateSoDoRaSan(soDo)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật sơ đồ ra sân thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật sơ đồ ra sân thất bại! Vui lòng kiểm tra dữ liệu.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật sơ đồ: " + e.getMessage());
            }
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sơ đồ để cập nhật!");
        }
    }

    private void deleteSoDoRaSan() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow != -1) {
            int maSoDo = (int) view.getTable().getValueAt(selectedRow, 0);
            int result = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa sơ đồ này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (service.deleteSoDoRaSan(maSoDo)) {
                    JOptionPane.showMessageDialog(view, "Xóa sơ đồ ra sân thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa sơ đồ ra sân thất bại!");
                }
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sơ đồ để xóa!");
        }
    }

    private void searchSoDoRaSan() {
        String searchText = view.getTxtSearch().getText().trim();
        if (!searchText.isEmpty()) {
            List<SoDo> searchResults = service.findSoDoRaSanByName(searchText);
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);
            for (SoDo soDo : searchResults) {
                model.addRow(new Object[]{
                    soDo.getMaSoDo(),
                    soDo.getTenSoDo(),
                    soDo.getLoaiSoDo(),
                    soDo.getChienThuat(),
                    soDo.getMaDoiBong()
                });
            }
            view.showMessage("Đã tìm thấy " + searchResults.size() + " kết quả.");
        } else {
            loadData();
            view.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
        }
    }


    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        int userSelection = fileChooser.showOpenDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try (FileInputStream inputStream = new FileInputStream(fileToImport);
                 Workbook workbook = new XSSFWorkbook(inputStream)) {

                Sheet sheet = workbook.getSheetAt(0);
                DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
                model.setRowCount(0);
                Iterator<Row> rowIterator = sheet.iterator();

                // Bỏ qua header
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                List<SoDo> importedSoDoList = new ArrayList<>();
                List<SoDo> existingSoDoList = service.getAllSoDoRaSan(); // Lấy dữ liệu hiện có từ DB
                int rowIndex = 1; // Bắt đầu từ dòng 1 (sau header)

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) {
                        SoDo soDo = new SoDo();
                        soDo.setTenSoDo(row.getCell(1).getStringCellValue());
                        soDo.setLoaiSoDo(row.getCell(2).getStringCellValue());
                        soDo.setChienThuat(row.getCell(3).getStringCellValue());
                        int maDoiBong = (int) getNumericCellValue(row.getCell(4));
                        soDo.setMaDoiBong(maDoiBong);

                        // Kiểm tra trùng lặp với dữ liệu hiện có trong DB
                        boolean isDuplicate = false;
                        for (SoDo existingSoDo : existingSoDoList) {
                            if (existingSoDo.getTenSoDo().equalsIgnoreCase(soDo.getTenSoDo()) &&
                                existingSoDo.getMaDoiBong() == soDo.getMaDoiBong()) {
                                isDuplicate = true;
                                System.out.println("Bỏ qua dòng " + (rowIndex + 1) + ": Trùng lặp với DB - TenSoDo: " + soDo.getTenSoDo());
                                break;
                            }
                        }
                        if (!isDuplicate && maDoiBong > 0) { // Chỉ thêm nếu không trùng và maDoiBong hợp lệ
                            importedSoDoList.add(soDo);
                            model.addRow(new Object[]{null, soDo.getTenSoDo(), soDo.getLoaiSoDo(), soDo.getChienThuat(), soDo.getMaDoiBong()});
                        } else if (maDoiBong <= 0) {
                            System.out.println("Bỏ qua dòng " + (rowIndex + 1) + ": MaDoiBong không hợp lệ: " + maDoiBong);
                        }
                        rowIndex++;
                    }
                }

                // Lưu vào cơ sở dữ liệu và ghi log chỉ một lần
                for (SoDo soDo : importedSoDoList) {
                    if (service.addSoDoRaSan(soDo)) {
                        System.out.println("Thêm thành công dòng " + (rowIndex - 1) + ": " + soDo.getTenSoDo() + " với MaSoDo: " + soDo.getMaSoDo());
                    } else {
                        view.showMessage("Lỗi khi thêm sơ đồ từ file dòng " + (rowIndex - 1) + ": " + soDo.getTenSoDo() + " (Kiểm tra MaDoiBong hoặc trùng lặp)");
                    }
                }
                loadData(); // Cập nhật lại bảng với dữ liệu từ DB
                view.showMessage("Nhập file Excel thành công: " + fileToImport.getAbsolutePath());
            } catch (Exception e) {
                view.showMessage("Lỗi khi nhập file Excel: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0;
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str); // Kiểm tra nếu có thể chuyển thành số nguyên
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void handleTableClick(MouseEvent e) {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Lấy dữ liệu từ model
                int maSoDo = (int) view.getModel().getValueAt(selectedRow, 0);
                String tenSoDo = (String) view.getModel().getValueAt(selectedRow, 1);
                String loaiSoDo = (String) view.getModel().getValueAt(selectedRow, 2);
                String chienThuat = (String) view.getModel().getValueAt(selectedRow, 3);
                int maDoiBong = (int) view.getModel().getValueAt(selectedRow, 4);

                // Điền dữ liệu vào các trường nhập liệu
                view.getTxtTenSoDo().setText(tenSoDo);
                view.getTxtLoaiSoDo().setText(loaiSoDo);
                view.getTxtChienThuat().setText(chienThuat);

                // Tìm và chọn đúng DoiBong trong JComboBox
                for (int i = 0; i < view.getCboMaDoiBong().getItemCount(); i++) {
                    DoiBong doiBong = view.getCboMaDoiBong().getItemAt(i);
                    if (doiBong.getMaDoiBong() == maDoiBong) {
                        view.getCboMaDoiBong().setSelectedIndex(i);
                        break;
                    }
                }

                System.out.println("Chọn dòng: " + selectedRow + ", MaSoDo: " + maSoDo); // Log
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi chọn sơ đồ: " + ex.getMessage());
                System.out.println("Lỗi khi chọn dòng: " + ex.getMessage()); // Log lỗi
            }
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("SơĐồRaSân.xlsx"));
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream outputStream = new FileOutputStream(fileToSave)) {

                Sheet sheet = workbook.createSheet("Sơ Đồ Ra Sân");
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
                    if (i == 0 || i == 4) { // Cột 0 (Mã Sơ Đồ) và 4 (Mã Đội Bóng)
                        cell.setCellStyle(numberStyle);
                    } else { // Cột 1, 2, 3 (Tên Sơ Đồ, Loại Sơ Đồ, Chiến Thuật)
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
                            if (j == 0 || j == 4) { // Cột 0 và 4 là số hoặc văn bản đặc biệt
                                if (isNumeric(valueStr)) {
                                    cell.setCellValue(Integer.parseInt(valueStr));
                                    cell.setCellStyle(numberStyle);
                                } else {
                                    cell.setCellValue(valueStr);
                                    cell.setCellStyle(textStyle);
                                }
                            } else { // Cột 1, 2, 3 là văn bản
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
}