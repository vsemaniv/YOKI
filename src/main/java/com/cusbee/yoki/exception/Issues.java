package com.cusbee.yoki.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class Issues extends HashMap<Integer, Issue> implements Serializable {

	private static final long serialVersionUID = 1L;

	public Issue add(Issue issue) {
		return super.put(issue.getCode(), issue);
	}

	public Issue remove(Issue issue) {
		return this.remove(issue.getCode());
	}

	public Issue remove(Integer issueCode) {
		return super.remove(issueCode);
	}

	public boolean isIssueExists(Issue issue) {
		return this.isIssueExists(issue.getCode());
	}
	
	public boolean isIssueExists(Integer issueCode) {
		return super.containsKey(issueCode);
	}

	public List<Issue> getIssues() {
		Set<Entry<Integer, Issue>> set = super.entrySet();
		List<Issue> issues = new ArrayList<Issue>();
		for (Entry<Integer, Issue> i : set) {
			issues.add(i.getValue());
		}
		return issues;

	}
}