package br.com.produban.agencia.ligacoes.reader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.produban.agencia.ligacoes.dto.LigacoesDto;

@RunWith(MockitoJUnitRunner.class)
public class ReadExcelFileServiceTest {
	
	
	@InjectMocks
	private ReadExcelFileService service;
	
	private Path path;
	
	@Before
	public void setup() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    
	    path = Paths.get("excelTeste.xlsx");
	    Files.deleteIfExists(path);
	    exportResource("/excelTeste.xlsx", path.toFile());
	    
	}
	
	static public void exportResource(String resourceName, File file) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = ReadExcelFileServiceTest.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(file);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }
    }
	
	@Test
	public void hasToReturn579Rows() {
		List<LigacoesDto> result = service.readFile(path);
		
		assertNotNull(result);
		assertEquals(result.size(), 759);
		
		LigacoesDto ligacoesDto = result.get(269);
		assertEquals(ligacoesDto.getRede(), "REDE SPI CENTRO E SUL MINAS");
		assertEquals(ligacoesDto.getRegional(), "REG SUL DE MINAS");
		assertEquals(ligacoesDto.getNome(), "EXTREMA");
		assertEquals(ligacoesDto.getCentral(), 53127);
		assertEquals(ligacoesDto.getUniorg(), 13127);
		assertEquals(ligacoesDto.getRamal(), 3313);
		assertEquals(ligacoesDto.getDataLigacao(), "16/05/17");
		assertEquals(ligacoesDto.getDiaSemana(), "Terça-Feira");
		assertEquals(ligacoesDto.getMes(), "MAI/2017");
		assertEquals(ligacoesDto.getSemanaMes(), "3");
		assertEquals(ligacoesDto.getHoraLigacao(), "15:55:19");
		assertEquals(ligacoesDto.getNumeroEntrada(), "35988627925");
		assertEquals(ligacoesDto.getDuracao(), 0);
		assertEquals(ligacoesDto.getQuantidade(), 1);
		assertEquals(ligacoesDto.getTipoRamal(), "Ramal");
		assertEquals(ligacoesDto.getTipo(), "Não atendida");
		assertEquals(ligacoesDto.getPin(), "8339649");
		assertEquals(ligacoesDto.getTotalPf(), 6162);
		assertEquals(ligacoesDto.getTotalPj(), 535);
		assertEquals(ligacoesDto.getPorte(), "B");
		
		
															 	        			

	}

}
