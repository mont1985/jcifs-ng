/*
 * © 2016 AgNO3 Gmbh & Co. KG
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package jcifs.config;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import jcifs.Configuration;
import jcifs.ResolverType;
import jcifs.SmbConstants;


/**
 * @author mbechler
 *
 */
public class BaseConfiguration implements Configuration {

    private static final Logger log = Logger.getLogger(PropertyConfiguration.class);
    private static final Map<String, Integer> DEFAULT_BATCH_LIMITS = new HashMap<>();


    static {
        DEFAULT_BATCH_LIMITS.put("TreeConnectAndX.QueryInformation", 0);
    }

    private final Map<String, Integer> batchLimits = new HashMap<>();

    protected int localPid = -1;
    protected TimeZone localTimeZone;
    protected SecureRandom random;
    protected boolean useBatching = true;
    protected boolean useUnicode = true;
    protected boolean forceUnicode = false;
    protected boolean signingPreferred = false;
    protected boolean signingEnforced = false;
    protected boolean useNtStatus = true;
    protected boolean useExtendedSecurity = true;
    protected boolean useNTSmbs = true;
    protected int lanmanCompatibility = 3;
    protected boolean disablePlainTextPasswords = true;
    protected String oemEncoding = SmbConstants.DEFAULT_OEM_ENCODING;
    protected int flags2 = 0;
    protected int capabilities = 0;
    protected int sessionLimit = SmbConstants.DEFAULT_SSN_LIMIT;
    protected boolean smbTcpNoDelay = false;
    protected int smbResponseTimeout = SmbConstants.DEFAULT_RESPONSE_TIMEOUT;
    protected int smbSocketTimeout = SmbConstants.DEFAULT_SO_TIMEOUT;
    protected int smbConnectionTimeout = SmbConstants.DEFAULT_CONN_TIMEOUT;
    protected int smbSessionTimeout = SmbConstants.DEFAULT_SO_TIMEOUT;
    protected InetAddress smbLocalAddress;
    protected int smbLocalPort = 0;
    protected int maxMpxCount = SmbConstants.DEFAULT_MAX_MPX_COUNT;
    protected int smbSendBufferSize = SmbConstants.DEFAULT_SND_BUF_SIZE;
    protected int smbRecvBufferSize = SmbConstants.DEFAULT_RCV_BUF_SIZE;
    protected int smbNotifyBufferSize = SmbConstants.DEFAULT_NOTIFY_BUF_SIZE;
    protected String nativeOs;
    protected String nativeLanMan = "jCIFS";
    protected int vcNumber = 1;
    protected boolean dfsDisabled = false;
    protected long dfsTTL = 300;
    protected boolean dfsStrictView = false;
    protected String logonShare;
    protected String defaultDomain;
    protected String defaultUserName;
    protected String defaultPassword;
    protected String netbiosHostname;
    protected int netbiosCachePolicy = 60 * 60 * 10;
    protected int netbiosSocketTimeout = 5000;
    protected int netbiosSendBufferSize = 576;
    protected int netbiosRevcBufferSize = 576;
    protected int netbiosRetryCount = 2;
    protected int netbiosRetryTimeout = 3000;
    protected String netbiosScope;
    protected int netbiosLocalPort = 0;
    protected InetAddress netbiosLocalAddress;
    protected String lmhostsFilename;
    protected InetAddress[] winsServer = new InetAddress[0];
    protected InetAddress broadcastAddress;
    protected List<ResolverType> resolverOrder;
    protected int transactionBufferSize = 0xFFFF - 512;
    protected int bufferCacheSize = 16;
    protected int smbListSize = 65535;
    protected int smbListCount = 200;
    protected long smbAttributeExpiration = 5000L;
    protected boolean ignoreCopyToException = true;
    private int maxRequestRetries = 2;
    private String[] supportedDialects = new String[] {
        "NT LM 0.12"
            // , "SMB 2.???",
    };


    /**
     * 
     */
    protected BaseConfiguration () {
        this(false);
    }


    /**
     * 
     * @param initDefaults
     *            whether to initialize defaults based on other settings
     */
    public BaseConfiguration ( boolean initDefaults ) {
        if ( initDefaults ) {
            this.initDefaults();
        }
    }


    @Override
    public SecureRandom getRandom () {
        return this.random;
    }


    @Override
    public String getNetbiosHostname () {
        return this.netbiosHostname;
    }


    @Override
    public InetAddress getLocalAddr () {
        return this.smbLocalAddress;
    }


    @Override
    public int getLocalPort () {
        return this.smbLocalPort;
    }


    @Override
    public int getConnTimeout () {
        return this.smbConnectionTimeout;
    }


    @Override
    public int getResponseTimeout () {
        return this.smbResponseTimeout;
    }


    @Override
    public int getSoTimeout () {
        return this.smbSocketTimeout;
    }


    @Override
    public int getSessionTimeout () {
        return this.smbSessionTimeout;
    }


    @Override
    public int getSendBufferSize () {
        return this.smbSendBufferSize;
    }


    @Override
    public int getRecieveBufferSize () {
        return this.smbRecvBufferSize;
    }


    @Override
    public int getNotifyBufferSize () {
        return this.smbNotifyBufferSize;
    }


    @Override
    public int getMaxMpxCount () {
        return this.maxMpxCount;
    }


    @Override
    public String getNativeLanman () {
        return this.nativeLanMan;
    }


    @Override
    public String getNativeOs () {
        return this.nativeOs;
    }


    @Override
    public int getVcNumber () {
        return this.vcNumber;
    }


    @Override
    public int getCapabilities () {
        return this.capabilities;
    }


    @Override
    public boolean isUseBatching () {
        return this.useBatching;
    }


    @Override
    public boolean isUseUnicode () {
        return this.useUnicode;
    }


    @Override
    public boolean isForceUnicode () {
        return this.forceUnicode;
    }


    @Override
    public boolean isDfsDisabled () {
        return this.dfsDisabled;
    }


    @Override
    public boolean isDfsStrictView () {
        return this.dfsStrictView;
    }


    @Override
    public long getDfsTtl () {
        return this.dfsTTL;
    }


    @Override
    public String getLogonShare () {
        return this.logonShare;
    }


    @Override
    public String getDefaultDomain () {
        return this.defaultDomain;
    }


    @Override
    public String getDefaultUsername () {
        return this.defaultUserName;
    }


    @Override
    public String getDefaultPassword () {
        return this.defaultPassword;
    }


    @Override
    public boolean isDisablePlainTextPasswords () {
        return this.disablePlainTextPasswords;
    }


    @Override
    public int getLanManCompatibility () {
        return this.lanmanCompatibility;
    }


    @Override
    public InetAddress getBroadcastAddress () {
        return this.broadcastAddress;
    }


    @Override
    public List<ResolverType> getResolveOrder () {
        return this.resolverOrder;
    }


    @Override
    public InetAddress[] getWinsServers () {
        return this.winsServer;
    }


    @Override
    public int getNetbiosLocalPort () {
        return this.netbiosLocalPort;
    }


    @Override
    public InetAddress getNetbiosLocalAddress () {
        return this.netbiosLocalAddress;
    }


    @Override
    public int getNetbiosSoTimeout () {
        return this.netbiosSocketTimeout;
    }


    @Override
    public String getNetbiosScope () {
        return this.netbiosScope;
    }


    @Override
    public int getNetbiosCachePolicy () {
        return this.netbiosCachePolicy;
    }


    @Override
    public int getNetbiosRcvBufSize () {
        return this.netbiosRevcBufferSize;
    }


    @Override
    public int getNetbiosRetryCount () {
        return this.netbiosRetryCount;
    }


    @Override
    public int getNetbiosRetryTimeout () {
        return this.netbiosRetryTimeout;
    }


    @Override
    public int getNetbiosSndBufSize () {
        return this.netbiosSendBufferSize;
    }


    @Override
    public String getLmHostsFileName () {
        return this.lmhostsFilename;
    }


    @Override
    public int getFlags2 () {
        return this.flags2;
    }


    @Override
    public int getSessionLimit () {
        return this.sessionLimit;
    }


    @Override
    public String getOemEncoding () {
        return this.oemEncoding;
    }


    @Override
    public TimeZone getLocalTimezone () {
        return this.localTimeZone;
    }


    @Override
    public int getPid () {
        return this.localPid;
    }


    @Override
    public boolean isSigningEnabled () {
        return this.signingPreferred;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.Configuration#isSigningEnforced()
     */
    @Override
    public boolean isSigningEnforced () {
        return this.signingEnforced;
    }


    @Override
    public int getTransactionBufferSize () {
        return this.transactionBufferSize;
    }


    @Override
    public int getBufferCacheSize () {
        return this.bufferCacheSize;
    }


    @Override
    public int getListCount () {
        return this.smbListCount;
    }


    @Override
    public int getListSize () {
        return this.smbListSize;
    }


    @Override
    public long getAttributeCacheTimeout () {
        return this.smbAttributeExpiration;
    }


    @Override
    public boolean isIgnoreCopyToException () {
        return this.ignoreCopyToException;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.Configuration#getSupportedDialects()
     */
    @Override
    public String[] getSupportedDialects () {
        return this.supportedDialects;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.Configuration#getMaxRequestRetries()
     */
    @Override
    public int getMaxRequestRetries () {
        return this.maxRequestRetries;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.Configuration#getBatchLimit(java.lang.String)
     */
    @Override
    public int getBatchLimit ( String cmd ) {
        Integer set = this.batchLimits.get(cmd);
        if ( set != null ) {
            return set;
        }

        set = doGetBatchLimit(cmd);
        if ( set != null ) {
            this.batchLimits.put(cmd, set);
            return set;
        }

        set = DEFAULT_BATCH_LIMITS.get(cmd);
        if ( set != null ) {
            return set;
        }
        return 1;
    }


    /**
     * @param cmd
     * @return
     */
    protected Integer doGetBatchLimit ( String cmd ) {
        return null;
    }


    protected void initResolverOrder ( String ro ) {
        this.resolverOrder = new ArrayList<>();
        if ( ro == null || ro.length() == 0 ) {
            /*
             * No resolveOrder has been specified, use the
             * default which is LMHOSTS,WINS,BCAST,DNS or just
             * LMHOSTS,BCAST,DNS if jcifs.netbios.wins has not
             * been specified.
             */
            if ( this.winsServer.length == 0 ) {
                this.resolverOrder.add(ResolverType.RESOLVER_LMHOSTS);
                this.resolverOrder.add(ResolverType.RESOLVER_DNS);
                this.resolverOrder.add(ResolverType.RESOLVER_BCAST);
            }
            else {
                this.resolverOrder.add(ResolverType.RESOLVER_LMHOSTS);
                this.resolverOrder.add(ResolverType.RESOLVER_DNS);
                this.resolverOrder.add(ResolverType.RESOLVER_WINS);
                this.resolverOrder.add(ResolverType.RESOLVER_BCAST);
            }
        }
        else {
            StringTokenizer st = new StringTokenizer(ro, ",");
            while ( st.hasMoreTokens() ) {
                String s = st.nextToken().trim();
                if ( s.equalsIgnoreCase("LMHOSTS") ) {
                    this.resolverOrder.add(ResolverType.RESOLVER_LMHOSTS);
                }
                else if ( s.equalsIgnoreCase("WINS") ) {
                    if ( this.winsServer.length == 0 ) {
                        log.error("UniAddress resolveOrder specifies WINS however " + " WINS server has not been configured");
                        continue;
                    }
                    this.resolverOrder.add(ResolverType.RESOLVER_WINS);
                }
                else if ( s.equalsIgnoreCase("BCAST") ) {
                    this.resolverOrder.add(ResolverType.RESOLVER_BCAST);
                }
                else if ( s.equalsIgnoreCase("DNS") ) {
                    this.resolverOrder.add(ResolverType.RESOLVER_DNS);
                }
                else {
                    log.error("unknown resolver method: " + s);
                }
            }
        }
    }


    protected void initDefaults () {
        this.localPid = (int) ( Math.random() * 65536d );
        this.localTimeZone = TimeZone.getDefault();
        this.random = new SecureRandom();

        if ( this.nativeOs == null ) {
            this.nativeOs = System.getProperty("os.name");
        }

        if ( this.flags2 == 0 ) {
            this.flags2 = SmbConstants.FLAGS2_LONG_FILENAMES | SmbConstants.FLAGS2_EXTENDED_ATTRIBUTES
                    | ( this.useExtendedSecurity ? SmbConstants.FLAGS2_EXTENDED_SECURITY_NEGOTIATION : 0 )
                    | ( this.signingPreferred ? SmbConstants.FLAGS2_SECURITY_SIGNATURES : 0 )
                    | ( this.useNtStatus ? SmbConstants.FLAGS2_STATUS32 : 0 )
                    | ( this.useUnicode || this.forceUnicode ? SmbConstants.FLAGS2_UNICODE : 0 );
        }

        if ( this.capabilities == 0 ) {
            this.capabilities = ( this.useNTSmbs ? SmbConstants.CAP_NT_SMBS : 0 ) | ( this.useNtStatus ? SmbConstants.CAP_STATUS32 : 0 )
                    | ( this.useExtendedSecurity ? SmbConstants.CAP_EXTENDED_SECURITY : 0 ) | SmbConstants.CAP_LARGE_READX
                    | SmbConstants.CAP_LARGE_WRITEX | ( this.useUnicode ? SmbConstants.CAP_UNICODE : 0 );
        }

        if ( this.broadcastAddress == null ) {
            try {
                this.broadcastAddress = InetAddress.getByName("255.255.255.255");
            }
            catch ( UnknownHostException uhe ) {
                log.debug("Failed to get broadcast address", uhe);
            }
        }

        if ( this.resolverOrder == null ) {
            initResolverOrder(null);
        }
    }

}