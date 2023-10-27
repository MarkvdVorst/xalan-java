package org.apache.xalan.trace;

import java.util.ArrayList;
import java.util.List;

public class TemplateTrace {
    private final String _templateName;
    private final String _parentTrace;
    private List<String> _childrenTraces;

    public TemplateTrace(String templateName, String parentTrace) {
        this._templateName = templateName;
        this._parentTrace = parentTrace;
        this._childrenTraces = new ArrayList<>();
    }

    public String GetTemplateName(){
        return _templateName;
    }

    public String GetParentTrace() {
        return _parentTrace;
    }

    public void AddChildTrace(String childTrace) {
        _childrenTraces.add(childTrace);
    }

    public String GetChildTrace(int index) {
        return _childrenTraces.get(index);
    }

    public List<String> GetAllChildTraces() {
        return _childrenTraces;
    }

    public void Flush() {
        _childrenTraces = new ArrayList<>();
    }
}
