package space.paraskun.postman.template.google;

import space.paraskun.postman.template.AbstractTemplate;

public abstract class AbstractSheetsTemplate extends AbstractTemplate {
	private String sheetName;

	public AbstractSheetsTemplate(String title, String sheetName) {
		super(title);
		this.sheetName = sheetName;
	}
}