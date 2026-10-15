package designPattern;

/*The State Design Pattern is a behavioral design pattern that lets an object change 
its behavior when its internal state changes, as if it were switching to a 
different class at runtime.*/

interface DocumentState{
	void edit(Report context,String content);
	void submitForReview(Report context);
	void approve(Report context);
	void reject(Report context);
	void unpublish(Report context);
}

class Report{
	private DocumentState currentState;
	private String content;
	
	public Report() {
		this.currentState = new DraftState();
		this.content = "";
	}
	
	public void setState(DocumentState state) {
		this.currentState = state;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
	
	void edit(String content) {
		currentState.edit(this, content);
	}
	void submitForReview() {
		currentState.submitForReview(this);
	}
	void approve() {
		currentState.approve(this);
	}
	void reject() {
		currentState.reject(this);
	}
	void unpublish() {
		currentState.unpublish(this);
	}
}

class DraftState implements DocumentState{

	@Override
	public void edit(Report context, String content) {
		System.out.println("Editing Documet" + content);
		context.setContent(content);
	}

	@Override
	public void submitForReview(Report context) {
		System.out.println("Document Submitted for Review");
		context.setState(new UnderReviewState());
	}

	@Override
	public void approve(Report context) {
		System.out.println("Cannot approve");
	}

	@Override
	public void reject(Report context) {
		System.out.println("Cannot Reject");
	}

	@Override
	public void unpublish(Report context) {
		System.out.println("Document is already a draft");
	}
}

class UnderReviewState implements DocumentState{

	@Override
	public void edit(Report context, String content) {
		System.out.println("Cannot edit while under review");
	}

	@Override
	public void submitForReview(Report context) {
		System.out.println("Document is already under review");
	}

	@Override
	public void approve(Report context) {
		System.out.println("Approved and published");
		context.setState(new PublishedState());
	}

	@Override
	public void reject(Report context) {
		System.out.println("Document Rejected. Returned to Draft State");
		context.setState(new DraftState());
	}

	@Override
	public void unpublish(Report context) {
		System.out.println("Document is not published yet");
	}
}

class PublishedState implements DocumentState{

	@Override
	public void edit(Report context, String content) {
		System.out.println("Cannot edit a published document. Unpublish it first");
	}

	@Override
	public void submitForReview(Report context) {
		System.out.println("Document is already published");
	}

	@Override
	public void approve(Report context) {
		System.out.println("Document is already published");
	}

	@Override
	public void reject(Report context) {
		System.out.println("Cannot Reject a published document");
	}

	@Override
	public void unpublish(Report context) {
		System.out.println("Document unpublied. Returing to draft");
		context.setState(new DraftState());
	}
	
}

public class State {
	public static void main(String[] args) {
		Report doc = new Report();
		
		doc.edit("First draft of the article.");
		doc.approve();              // Rejected: cannot approve a draft
		doc.submitForReview();
		doc.edit("Trying to edit");  // Rejected: under review
		doc.reject();                // Back to draft
		doc.edit("Revised draft.");
		doc.submitForReview();
		doc.approve();               // Published
		doc.edit("Trying to edit");  // Rejected: published
		doc.unpublish(); 		
	}

}


/*
 * Bad Code
 * 
 * class Report { private String state = "DRAFT"; private String content = "";
 * 
 * void edit(String content) { if (state.equals("DRAFT")) {
 * System.out.println("Editing document: " + content); this.content = content; }
 * else if (state.equals("UNDER_REVIEW")) {
 * System.out.println("Cannot edit while under review"); } else if
 * (state.equals("PUBLISHED")) {
 * System.out.println("Cannot edit published document"); } }
 * 
 * void submitForReview() { if (state.equals("DRAFT")) {
 * System.out.println("Document submitted for review"); state = "UNDER_REVIEW";
 * } else if (state.equals("UNDER_REVIEW")) {
 * System.out.println("Already under review"); } else if
 * (state.equals("PUBLISHED")) { System.out.println("Already published"); } }
 * 
 * void approve() { if (state.equals("DRAFT")) {
 * System.out.println("Cannot approve a draft"); } else if
 * (state.equals("UNDER_REVIEW")) {
 * System.out.println("Approved and published"); state = "PUBLISHED"; } else if
 * (state.equals("PUBLISHED")) { System.out.println("Already published"); } }
 * 
 * void reject() { if (state.equals("DRAFT")) {
 * System.out.println("Cannot reject a draft"); } else if
 * (state.equals("UNDER_REVIEW")) { System.out.println("Document rejected");
 * state = "DRAFT"; } else if (state.equals("PUBLISHED")) {
 * System.out.println("Cannot reject published document"); } }
 * 
 * void unpublish() { if (state.equals("DRAFT")) {
 * System.out.println("Already a draft"); } else if
 * (state.equals("UNDER_REVIEW")) {
 * System.out.println("Document is not published yet"); } else if
 * (state.equals("PUBLISHED")) { System.out.println("Document unpublished");
 * state = "DRAFT"; } } }
 */
