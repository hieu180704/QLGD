package Controller;

import Model.NhaTaiTro;
import Service.NhaTaiTroService;
import View.Admin.NhaTaiTroPanel.NhaTaiTroPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhaTaiTroController {
    private NhaTaiTroPanel view;
    private NhaTaiTroService service;

    public NhaTaiTroController(NhaTaiTroPanel view) {
        this.view = view;
        this.service = new NhaTaiTroService();
        addEventListeners();
    }

    private void addEventListeners() {
        // Sự kiện chọn dòng trên bảng
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    handleTableSelection();
                }
            }
        });

        // Sự kiện chọn ảnh
        view.getBtnChonAnh().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChooseImage();
            }
        });

        // Sự kiện thêm
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAdd();
            }
        });

        // Sự kiện sửa
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });

        // Sự kiện xóa
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });

        // Sự kiện tìm kiếm
        view.getBtnTim().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        // Sự kiện xuất Excel
        view.getBtnExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        // Sự kiện nhập Excel
        view.getBtnImport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromExcel();
            }
        });
    }

    private void handleTableSelection() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            view.getTxtTen().setText(view.getModel().getValueAt(selectedRow, 1).toString());
            view.getTxtEmail().setText(view.getModel().getValueAt(selectedRow, 2).toString());
            view.getTxtSDT().setText(view.getModel().getValueAt(selectedRow, 3).toString());
            view.getTxtDiaChi().setText(view.getModel().getValueAt(selectedRow, 4).toString());
            // Logo không được gán lại vì là byte[], cần xử lý riêng nếu cần
        }
    }

    private void handleChooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(view);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                view.setLogoBytes(java.nio.file.Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
                view.showMessage("Đã chọn ảnh logo.");
            } catch (Exception ex) {
                view.showMessage("Lỗi đọc file ảnh.");
            }
        }
    }

    private void handleAdd() {
        NhaTaiTro ntt = new NhaTaiTro(
                view.getTxtTen().getText(),
                view.getLogoBytes(),
                view.getTxtEmail().getText(),
                view.getTxtSDT().getText(),
                view.getTxtDiaChi().getText()
        );
        String result = service.addNhaTaiTro(ntt);
        view.showMessage(result);
        if (result.contains("thành công")) {
            view.loadData();
            view.clearInput();
        }
    }

    private void handleUpdate() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Chọn nhà tài trợ cần sửa!");
            return;
        }
        int maNTT = (int) view.getModel().getValueAt(selectedRow, 0);
        NhaTaiTro ntt = new NhaTaiTro(
                maNTT,
                view.getTxtTen().getText(),
                view.getLogoBytes(),
                view.getTxtEmail().getText(),
                view.getTxtSDT().getText(),
                view.getTxtDiaChi().getText()
        );
        String result = service.updateNhaTaiTro(ntt);
        view.showMessage(result);
        if (result.contains("thành công")) {
            view.loadData();
            view.clearInput();
        }
    }

    private void handleDelete() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Chọn nhà tài trợ cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa nhà tài trợ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int maNTT = (int) view.getModel().getValueAt(selectedRow, 0);
            String result = service.deleteNhaTaiTro(maNTT);
            view.showMessage(result);
            if (result.contains("thành công")) {
                view.loadData();
                view.clearInput();
            }
        }
    }

    private void handleSearch() {
        String keyword = view.getTxtTimKiem().getText().trim();
        List<NhaTaiTro> list = service.searchNhaTaiTro(keyword);
        view.displayData(list);
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("NhaTaiTro.xlsx"));
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream outputStream = new FileOutputStream(fileToSave)) {

                Sheet sheet = workbook.createSheet("Danh Sách Nhà Tài Trợ");
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
                    if (i == 0) { // Cột 0 (Mã NTT)
                        cell.setCellStyle(numberStyle);
                    } else { // Cột 1, 2, 3, 4, 5 (Tên, Email, SĐT, Địa Chỉ, Logo)
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
                            if (j == 0) { // Cột 0 (Mã NTT)
                                try {
                                    cell.setCellValue(Integer.parseInt(valueStr));
                                    cell.setCellStyle(numberStyle);
                                } catch (NumberFormatException e) {
                                    cell.setCellValue(valueStr);
                                    cell.setCellStyle(textStyle);
                                }
                            } else if (j == 5) { // Cột 5 (Logo)
                                // Lưu logo dưới dạng đường dẫn hoặc base64 (ở đây chỉ lưu chuỗi trống nếu null)
                                cell.setCellValue(valueStr.isEmpty() ? "No Logo" : valueStr);
                                cell.setCellStyle(textStyle);
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

                List<NhaTaiTro> importedNhaTaiTroList = new ArrayList<>();
                List<NhaTaiTro> existingNhaTaiTroList = service.getAllNhaTaiTro(); // Lấy dữ liệu hiện có từ DB
                int rowIndex = 1; // Bắt đầu từ dòng 1 (sau header)

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null) {
                        NhaTaiTro ntt = new NhaTaiTro();
                        ntt.setTenNTT(row.getCell(1).getStringCellValue());
                        ntt.setEmail(row.getCell(2).getStringCellValue());
                        ntt.setSoDienThoai(row.getCell(3).getStringCellValue());
                        ntt.setDiaChi(row.getCell(4).getStringCellValue());
                        // Logo được bỏ qua vì không nhập từ file Excel, giữ null
                        ntt.setLogoNTT(null); // Hoặc xử lý nếu có đường dẫn/base64 trong cột 5

                        // Kiểm tra trùng lặp với dữ liệu hiện có trong DB (dựa trên tên và email)
                        boolean isDuplicate = false;
                        for (NhaTaiTro existingNtt : existingNhaTaiTroList) {
                            if (existingNtt.getTenNTT().equalsIgnoreCase(ntt.getTenNTT()) &&
                                existingNtt.getEmail().equalsIgnoreCase(ntt.getEmail())) {
                                isDuplicate = true;
                                System.out.println("Bỏ qua dòng " + (rowIndex + 1) + ": Trùng Tên và Email - TenNTT: " + ntt.getTenNTT());
                                break;
                            }
                        }
                        if (!isDuplicate) {
                            importedNhaTaiTroList.add(ntt);
                            model.addRow(new Object[]{null, ntt.getTenNTT(), ntt.getEmail(), ntt.getSoDienThoai(), ntt.getDiaChi(), null});
                        }
                        rowIndex++;
                    }
                }

                // Lưu vào cơ sở dữ liệu
                for (NhaTaiTro ntt : importedNhaTaiTroList) {
                    System.out.println("Thử thêm nhà tài trợ dòng " + rowIndex + ": " + ntt.getTenNTT());
                    String result = service.addNhaTaiTro(ntt);
                    if (result.contains("thành công")) {
                        System.out.println("Thêm thành công dòng " + rowIndex + ": " + ntt.getTenNTT());
                    } else {
                        view.showMessage("Lỗi khi thêm nhà tài trợ từ file dòng " + rowIndex + ": " + ntt.getTenNTT() + " (" + result + ")");
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