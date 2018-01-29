package br.com.produban.agencia.ligacoes.reader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.produban.agencia.ligacoes.dto.LigacoesDto;

public class ReadExcelFileService {

	public List<LigacoesDto> readFile(Path path) {
		List<LigacoesDto> list = new ArrayList<LigacoesDto>();
		try {

			File file = path.toFile();

			String name = file.toString();

			int pos = name.lastIndexOf('.');

			String ext = name.substring(pos + 1);

			FileInputStream fileIn = new FileInputStream(file);

			Workbook obj = null;

			if (ext.equals("xlsx")) {

				try {
					// Metodo aceita o path do arquivo
					obj = new XSSFWorkbook(fileIn);
				}

				catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

			else if (ext.equals("xls")) {

				try {
					// Metodo nao aceita string do path do arquivo
					obj = new HSSFWorkbook(fileIn);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			else {
				throw new IllegalArgumentException("Arquivo recebido não é uma extensão do Excel");
			}

			int o = 0;

			Sheet worksheet = obj.getSheet("base");

			Row row;

			Cell cell;

			
			for (int i = 1; i <= worksheet.getLastRowNum(); i++) {

				row = worksheet.getRow(i);
				LigacoesDto excel = new LigacoesDto();
				excel.setRede(row.getCell(0).getStringCellValue());
				excel.setRegional(row.getCell(1).getStringCellValue());
				excel.setNome(row.getCell(2).getStringCellValue());
				excel.setCentral((int)row.getCell(3).getNumericCellValue());
				excel.setUniorg( (int)row.getCell(4).getNumericCellValue());
				excel.setRamal( (int) row.getCell(5).getNumericCellValue());
				excel.setDataLigacao(row.getCell(6).getStringCellValue());
				excel.setDiaSemana(row.getCell(7).getStringCellValue());
				excel.setMes(row.getCell(8).getStringCellValue());
				excel.setSemanaMes(row.getCell(9).getStringCellValue());
				excel.setHoraLigacao( row.getCell(10).getStringCellValue());
				excel.setNumeroEntrada( row.getCell(11).getStringCellValue());
				excel.setDuracao( (int) row.getCell(12).getNumericCellValue());
				excel.setQuantidade( (int) row.getCell(13).getNumericCellValue());
				excel.setTipoRamal( row.getCell(14).getStringCellValue());
				excel.setTipo(row.getCell(15).getStringCellValue());
				excel.setPin(row.getCell(16).getStringCellValue().trim());
				excel.setTotalPf( (int) row.getCell(17).getNumericCellValue());
				excel.setTotalPj( (int) row.getCell(18).getNumericCellValue());
				excel.setPorte(row.getCell(19).getStringCellValue());
				list.add(excel);
			}

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
}
