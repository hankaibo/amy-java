package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.modular.system.mapper.RegionMapper;
import cn.mypandora.springboot.modular.system.model.po.BaseEntity;
import cn.mypandora.springboot.modular.system.model.po.Region;
import cn.mypandora.springboot.modular.system.service.RegionService;
import tk.mybatis.mapper.entity.Example;

/**
 * RegionServiceImpl
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    @Autowired
    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public List<Region> listRegion(StatusEnum status) {
        return regionMapper.listAll(status);
    }

    @Override
    public List<Region> listChildrenRegion(Long id, StatusEnum status) {
        return regionMapper.listChildren(id, status);
    }

    @Override
    public void addRegion(Region region) {
        LocalDateTime now = LocalDateTime.now();
        // 添加到父区域末尾
        Long parentId = region.getParentId();
        if (parentId == null) {
            Example example = new Example(Region.class);
            example.createCriteria().andEqualTo("lft", 1);
            Region info = regionMapper.selectOneByExample(example);
            if (info == null) {
                Region root = new Region();
                root.setParentId(null);
                root.setName("根");
                root.setCode("root");
                root.setLft(1);
                root.setRgt(2);
                root.setLevel(1);
                root.setStatus(StatusEnum.ENABLED);
                root.setDescription("根区域");
                root.setIsUpdate(1);
                root.setCreateTime(now);
                regionMapper.insert(root);
                //
                parentId = root.getId();
            }
        }
        Region parentRegion = getRegionById(parentId);
        region.setCreateTime(now);
        region.setLft(parentRegion.getRgt());
        region.setRgt(parentRegion.getRgt() + 1);
        region.setLevel(parentRegion.getLevel() + 1);
        region.setIsUpdate(1);

        int amount = 2;
        // 将树形结构中所有大于父区域右值的左区域+2
        regionMapper.lftAdd(parentId, amount, null);
        // 将树形结构中所有大于父区域右值的右区域+2
        regionMapper.rgtAdd(parentId, amount, null);
        // 插入新区域
        regionMapper.insert(region);
    }

    @Override
    public Region getRegionById(Long id) {
        Region info = regionMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new EntityNotFoundException(Region.class, "区域不存在。");
        }
        return info;
    }

    @Override
    public List<Region> listRegionByCode(List<String> codeList) {
        Example example = new Example(Region.class);
        example.createCriteria().andIn("code", codeList);
        List<Region> regionList = regionMapper.selectByExample(example);
        List<Long> longList = regionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (longList.size() > 0) {
            Example params = new Example(Region.class);
            params.createCriteria().andIn("parentId", longList);
            regionList.addAll(regionMapper.selectByExample(params));
            return regionList;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRegion(Region region) {
        // 修改区域时，不可以指定自己的下级为父区域。
        if (!isCanUpdateParent(region)) {
            throw new BusinessException(Region.class, "不可以选择子区域作为自己的父级。");
        }

        // 修改区域
        LocalDateTime now = LocalDateTime.now();
        region.setUpdateTime(now);

        // 如果父级区域相等，则直接修改其它属性
        Region info = getRegionById(region.getId());
        if (!info.getParentId().equals(region.getParentId())) {
            // 求出新旧两个父区域的最近共同祖先区域，减小修改范围。
            Region newParentRegion = getRegionById(region.getParentId());
            Region oldParentRegion = getRegionById(info.getParentId());
            Region commonAncestry = getCommonAncestry(newParentRegion, oldParentRegion);

            // 避免下面修改了共同祖先区域的右区域值。
            int range = commonAncestry.getRgt() + 1;

            // 要修改的区域及子孙区域共多少个
            List<Long> updateIdList = listDescendantId(region.getId());
            int regionNum = updateIdList.size();

            // 首先锁定被修改区域及子孙区域，保证左右值不会被下面操作修改。
            regionMapper.locking(updateIdList, 0);

            // 先将修改区域摘出来，它之后的区域区域值修改
            Long oldId = info.getId();
            int oldAmount = regionNum * -2;
            regionMapper.lftAdd(oldId, oldAmount, range);
            regionMapper.rgtAdd(oldId, oldAmount, range);

            // 将修改区域插入到新父区域之后左右值修改
            Long newParentId = newParentRegion.getId();
            int newAmount = regionNum * 2;
            regionMapper.lftAdd(newParentId, newAmount, range);
            regionMapper.rgtAdd(newParentId, newAmount, range);

            // 被修改区域及子孙区域左右值修改
            regionMapper.locking(updateIdList, 1);
            int amount = getRegionById(region.getParentId()).getRgt() - info.getRgt() - 1;
            int level = newParentRegion.getLevel() + 1 - info.getLevel();
            regionMapper.selfAndDescendant(updateIdList, amount, level);

            // 修改本身
            region.setLevel(newParentRegion.getLevel() + 1);
        }

        regionMapper.updateByPrimaryKeySelective(region);
    }

    @Override
    public void enableRegion(Long id, StatusEnum status) {
        // 修改本区域及子孙区域状态
        List<Long> idList = listDescendantId(id);
        regionMapper.enableDescendants(idList, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRegion(Long id) {
        // 求出要删除的区域所有子孙区域
        List<Long> idList = listDescendantId(id);
        String ids = StringUtils.join(idList, ',');

        // 先求出要删除的区域的所有信息，利用左值与右值计算出要删除的区域数量。
        // 删除区域数=(区域右值-区域左值+1)/2
        Region info = regionMapper.selectByPrimaryKey(id);
        int deleteAmount = info.getRgt() - info.getLft() + 1;

        // 更新此区域之后的相关区域左右值
        regionMapper.lftAdd(id, -deleteAmount, null);
        regionMapper.rgtAdd(id, -deleteAmount, null);

        // 批量删除区域及子孙区域
        regionMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveRegion(Long sourceId, Long targetId) {
        Region sourceInfo = getRegionById(sourceId);
        Region targetInfo = getRegionById(targetId);

        if (null == sourceInfo || null == targetInfo) {
            throw new BusinessException(Region.class, "所选区域错误。");
        }

        // 同层级
        if (!(sourceInfo.getLevel().equals(targetInfo.getLevel()))) {
            throw new BusinessException(Region.class, "所选区域错误，不是同级区域。");
        }
        // 相邻
        if (Math.abs(sourceInfo.getRgt() - targetInfo.getLft()) != 1
            && (Math.abs(sourceInfo.getLft() - targetInfo.getRgt()) != 1)) {
            throw new BusinessException(Region.class, "所选区域错误，不是相邻区域。");
        }

        int sourceAmount = sourceInfo.getRgt() - sourceInfo.getLft() + 1;
        int targetAmount = targetInfo.getRgt() - targetInfo.getLft() + 1;

        List<Long> sourceIdList = listDescendantId(sourceId);
        List<Long> targetIdList = listDescendantId(targetId);

        // 确定方向，目标大于源：下移；反之：上移。
        if (targetInfo.getRgt() > sourceInfo.getRgt()) {
            sourceAmount *= -1;
        } else {
            targetAmount *= -1;
        }
        // 源区域及子孙区域左右值 targetAmount
        regionMapper.selfAndDescendant(sourceIdList, targetAmount, 0);
        // 目标区域及子孙区域左右值 sourceAmount
        regionMapper.selfAndDescendant(targetIdList, sourceAmount, 0);
    }

    /**
     * 获取此区域及其子孙区域的id。
     *
     * @param id
     *            区域主键id
     * @return 区域主键id集合
     */
    private List<Long> listDescendantId(Long id) {
        List<Region> regionList = regionMapper.listDescendants(id, null);
        List<Long> idList = regionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        idList.add(id);
        return idList;
    }

    /**
     * 防止更新区域时，指定自己的下级区域作为自己的父级区域。
     *
     * @param region
     *            区域
     * @return true可以更新；false不可以更新
     */
    private boolean isCanUpdateParent(Region region) {
        Region childRegion = regionMapper.selectByPrimaryKey(region.getId());

        Region parentRegion = regionMapper.selectByPrimaryKey(region.getParentId());
        return !(parentRegion.getLft() >= childRegion.getLft()
            && parentRegion.getRgt() <= childRegion.getRgt());
    }

    /**
     * 获取两个区域最近的共同祖先区域。
     *
     * @param region1
     *            第一个区域
     * @param region2
     *            第二个区域
     * @return 最近的祖先区域
     */
    private Region getCommonAncestry(Region region1, Region region2) {
        // 首先判断两者是否是包含关系
        if (region1.getLft() < region2.getLft() && region1.getRgt() > region2.getRgt()) {
            return region1;
        }
        if (region2.getLft() < region1.getLft() && region2.getRgt() > region1.getRgt()) {
            return region2;
        }
        // 两者没有包含关系的情况下
        Long newId = region1.getId();
        List<Region> newParentAncestries = regionMapper.listAncestries(newId, StatusEnum.ENABLED);
        if (newParentAncestries.size() == 0) {
            newParentAncestries.add(region1);
        }

        Long oldId = region2.getId();
        List<Region> oldParentAncestries = regionMapper.listAncestries(oldId, StatusEnum.ENABLED);
        if (oldParentAncestries.size() == 0) {
            oldParentAncestries.add(region2);
        }

        Comparator<Region> comparator = Comparator.comparing(Region::getLft);
        return newParentAncestries.stream().filter(oldParentAncestries::contains).max(comparator)
            .orElseThrow(RuntimeException::new);
    }
}
