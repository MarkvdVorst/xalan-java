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

    /**Gets the systemId of the template*/
    public String GetTemplateName(){
        return _templateName;
    }

    /**Gets the entire first trace that mentions the template location and the match for it*/
    public String GetParentTrace() {
        return _parentTrace;
    }

    /**This method adds a trace to the children traces of the parent template trace*/
    public void AddChildTrace(String childTrace) {
        _childrenTraces.add(childTrace);
    }

    /**Get a child trace from a specific index*/
    public String GetChildTrace(int index) {
        return _childrenTraces.get(index);
    }

    /**Returns all child traces that were added*/
    public List<String> GetAllChildTraces() {
        return _childrenTraces;
    }

    /**Empties the child traces for this template trace*/
    public void Flush() {
        _childrenTraces = new ArrayList<>();
    }

    /**Returns a string that holds the complete trace of the transform*/
    public String GetWholeTrace(boolean showSeperator){
        StringBuilder result = new StringBuilder();

        if(showSeperator) {
            result.append("--------------------------------------------New template being applied--------------------------------------------");
        }
        result.append(_parentTrace);

        for (String childrenTrace : _childrenTraces) {
            result.append(childrenTrace);
        }

        return result.toString();
    }
}
