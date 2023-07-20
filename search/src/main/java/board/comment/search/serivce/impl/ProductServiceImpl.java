package board.comment.search.serivce.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import board.comment.search.dto.product.ProductDTO;
import board.comment.search.dto.product.ProductListDTO;
import board.comment.search.entity.Product;
import board.comment.search.repository.ProductRepository;
import board.comment.search.serivce.ProductService;
import board.comment.search.util.FileUploder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    // 의존성 주입
    private final ProductRepository productRepository;
    private final FileUploder fileUploder;

    // Autowired 명시적 표시
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, FileUploder fileUploder) {
        log.info("Constructor Called, Repository Injected.");
        this.productRepository = productRepository;
        this.fileUploder = fileUploder;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductListDTO> listProduct(PageRequestDTO pageRequestDTO) {
        log.info("Is Running ProductRepsotiry List");
        return productRepository.listWithReview(pageRequestDTO);
    }

    @Override
    @Transactional
    public Long createProduct(ProductDTO productDTO) {
        log.info("Is Running ProductRepository Create");
        Product productEntity = Product.builder()
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();
        productDTO.getImages().forEach(fname -> {
            productEntity.addImage(fname);
        });
        return productRepository.save(productEntity).getPno();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO readProduct(Long pno) {
        log.info("Is Running ProductRepository Read");
        Product productEntity = productRepository.selectOne(pno);
        ProductDTO productDTO = ProductDTO.builder()
                .pno(productEntity.getPno())
                .pname(productEntity.getPname())
                .price(productEntity.getPrice())
                .pdesc(productEntity.getPdesc())
                .images(productEntity.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
                .build();
        return productDTO;
    }

    @Override
    @Transactional
    public void deleteProduct(Long pno) {
        log.info("Is Running ProductRepository Delete");
        Product productEntity = productRepository.selectOne(pno);
        // Entity에 changeFlag 설정
        productEntity.changeDeleteFlag(true);
        productRepository.save(productEntity);

        List<String> fileNames = productEntity.getImages().stream().map(pi -> pi.getFname())
                .collect(Collectors.toList());
        // Enginx File Delete
        fileUploder.removeFiles(fileNames);
    }

    @Override
    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        log.info("Is Running ProductRepository Update ");
        Optional<Product> result = productRepository.findById(productDTO.getPno());
        Product productEntity = result.orElseThrow();

        productEntity.changePdesc(productDTO.getPdesc());
        productEntity.changePname(productDTO.getPname());
        productEntity.changePrice(productDTO.getPrice());
        // 기존 이미지 목록들을 살린다 => 나중에 비교해서 삭제
        List<String> oldFileNames = productEntity.getImages().stream().map(pi -> pi.getFname())
                .collect(Collectors.toList());
        // 이미지 삭제
        productEntity.clearImages();
        // 이미지 추가
        productDTO.getImages().forEach(fname -> productEntity.addImage(fname));
        productRepository.save(productEntity);
        /*
         * 기존 파일들 중 productDTO.getImages() 에 없는 파일을 찾기
         * filter로 찾은후 uploder 를 호출해 Enginx 에 있는 파일을 삭제 한다
         */
        List<String> newFiles = productDTO.getImages();
        List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1)
                .collect(Collectors.toList());
        fileUploder.removeFiles(wantDeleteFiles);
    }
}
