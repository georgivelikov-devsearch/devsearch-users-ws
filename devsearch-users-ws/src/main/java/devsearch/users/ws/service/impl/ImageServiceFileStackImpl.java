package devsearch.users.ws.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import devsearch.users.ws.service.ImageService;
import devsearch.users.ws.ui.model.response.FileStackResponse;

@Service
public class ImageServiceFileStackImpl implements ImageService {
    String fileStackUrl = "https://www.filestackapi.com/api/store/s3?key={key}&filename={name}&type={type}";

    @Override
    public String saveImageAndReturnURL(String base64EncodedSource, String newFileName) {
	byte[] content = DatatypeConverter.parseBase64Binary(base64EncodedSource);

	RestTemplate client = new RestTemplate();

	// filestack.com api key - AnEmTECOR4qEhZPsQS4D0z - should be private
	Map<String, String> uriVariables = new HashMap<>();
	uriVariables.put("key", "AnEmTECOR4qEhZPsQS4D0z");
	uriVariables.put("filename", newFileName);
	uriVariables.put("type", "image/png");

	HttpEntity<byte[]> request = new HttpEntity<byte[]>(content);
	HttpEntity<FileStackResponse> response = client.exchange(fileStackUrl, HttpMethod.POST, request,
		FileStackResponse.class, "AnEmTECOR4qEhZPsQS4D0z", newFileName, "image/png");

	return response.getBody().getUrl();
    }
}
