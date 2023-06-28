package net.chembot.betterrailinterfaces;

public interface IEntityFilter
{
    public Class getFilterClass();
    public boolean isFilter();
    public void setFilterClass(Class clazz);
}
