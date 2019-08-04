package games.bevs.core.commons.database;

public abstract class DatabaseOperation
{
    private boolean success;

    //connect mysql
    //connect redis

    public boolean isSuccess()
    {
        return success;
    }

    protected void setSuccess()
    {
        success = true;
    }
}