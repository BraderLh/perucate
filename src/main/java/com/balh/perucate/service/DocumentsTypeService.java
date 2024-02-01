package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestDocumentType;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface DocumentsTypeService {
    ResponseBase createDocumentsType(RequestDocumentType requestDocumentType);
    ResponseBase deleteDocumentsType(Integer id);
    ResponseBase findOneDocument(Integer id);
    ResponseBase findAllDocumentsType();
    ResponseBase updateDocumentsType(Integer id, RequestDocumentType requestDocumentType);
}
